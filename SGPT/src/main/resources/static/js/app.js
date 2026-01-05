const API_URL = 'http://localhost:8080/api';
let token = localStorage.getItem('token');

// Init
document.addEventListener('DOMContentLoaded', () => {
    if (token) {
        showDashboard();
    } else {
        showLogin();
    }
});

// UI State Management
function showLogin() {
    document.getElementById('auth-view').style.display = 'block';
    document.getElementById('dashboard-view').style.display = 'none';
    document.getElementById('login-form').style.display = 'block';
    document.getElementById('register-form').style.display = 'none';
    document.querySelector('.tabs button:nth-child(1)').classList.add('active');
    document.querySelector('.tabs button:nth-child(2)').classList.remove('active');
}

function showRegister() {
    document.getElementById('login-form').style.display = 'none';
    document.getElementById('register-form').style.display = 'block';
    document.querySelector('.tabs button:nth-child(1)').classList.remove('active');
    document.querySelector('.tabs button:nth-child(2)').classList.add('active');
}

function showDashboard() {
    document.getElementById('auth-view').style.display = 'none';
    document.getElementById('dashboard-view').style.display = 'block';
    document.getElementById('current-user').textContent = localStorage.getItem('username');
    loadProjects();
}

function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    token = null;
    showLogin();
}

// API Calls
async function handleLogin(e) {
    e.preventDefault();
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            token = data.token;
            localStorage.setItem('token', token);
            localStorage.setItem('username', data.username);
            showDashboard();
        } else {
            showError('Login failed');
        }
    } catch (error) {
        showError('Network error');
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const username = document.getElementById('reg-username').value;
    const email = document.getElementById('reg-email').value;
    const password = document.getElementById('reg-password').value;

    try {
        const response = await fetch(`${API_URL}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, email, password })
        });

        if (response.ok) {
            showError('Registration successful! Please login.', 'green');
            showLogin();
        } else {
            showError('Registration failed');
        }
    } catch (error) {
        showError('Network error');
    }
}

async function loadProjects() {
    try {
        const response = await fetch(`${API_URL}/projects`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        const projects = await response.json();
        renderProjects(projects);
    } catch (error) {
        if (error.status === 401) logout();
    }
}

async function createProject() {
    const nameInput = document.getElementById('new-project-name');
    const name = nameInput.value;
    if (!name) return;

    const response = await fetch(`${API_URL}/projects`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name })
    });

    if (response.ok) {
        nameInput.value = '';
        loadProjects();
    }
}

async function activateProject(id) {
    const response = await fetch(`${API_URL}/projects/${id}/activate`, {
        method: 'PATCH',
        headers: { 'Authorization': `Bearer ${token}` }
    });
    if (response.ok) loadProjects();
    else alert('Cannot activate project. Ensure it has at least one task.');
}

async function createTask(projectId) {
    const title = prompt('Task Title:');
    if (!title) return;

    await fetch(`${API_URL}/projects/${projectId}/tasks`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title })
    });
    loadProjects();
}

function renderProjects(projects) {
    const container = document.getElementById('projects-list');
    container.innerHTML = projects.map(p => `
        <div class="project-card">
            <div class="project-header">
                <h3>${p.name}</h3>
                <span class="status-badge status-${p.status}">${p.status}</span>
            </div>
            <div class="actions">
                <button onclick="createTask('${p.id}')">Add Task</button>
                ${p.status === 'DRAFT' ?
            `<button onclick="activateProject('${p.id}')">Activate</button>` : ''}
            </div>
        </div>
    `).join('');
}

function showError(msg, color = 'red') {
    const el = document.getElementById('auth-message');
    el.textContent = msg;
    el.style.color = color;
}
