const API_URL = 'http://localhost:8080/api';
let token = localStorage.getItem('token');

// Init
document.addEventListener('DOMContentLoaded', () => {
    // Force login view on initial load as per user request
    token = null; // Reset token for demo purposes
    showLogin();
});

// Toast Notification System
function showToast(message, type = 'info') {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.textContent = message;

    container.appendChild(toast);

    setTimeout(() => {
        toast.style.animation = 'slideOut 0.3s ease-in forwards';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

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
            showToast(`Welcome back, ${data.username}!`, 'success');
        } else {
            showError('Login failed');
            showToast('Login failed', 'error');
        }
    } catch (error) {
        showError('Network error');
        showToast('Network error', 'error');
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
            showToast('Registration successful! Please login.', 'success');
            showLogin();
        } else {
            showError('Registration failed');
            showToast('Registration failed', 'error');
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

        // Fetch tasks for each project
        const projectsWithTasks = await Promise.all(projects.map(async (project) => {
            const tasksResponse = await fetch(`${API_URL}/projects/${project.id}/tasks`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            const tasks = await tasksResponse.json();
            return { ...project, tasks };
        }));

        renderProjects(projectsWithTasks);
    } catch (error) {
        if (error.status === 401) logout();
    }
}

async function createProject() {
    const nameInput = document.getElementById('new-project-name');
    const name = nameInput.value;
    if (!name) return;

    try {
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
            showToast('Project created successfully', 'success');
            loadProjects();
        } else {
            showToast('Failed to create project', 'error');
        }
    } catch (e) {
        showToast('Error creating project', 'error');
    }
}

async function activateProject(id) {
    try {
        const response = await fetch(`${API_URL}/projects/${id}/activate`, {
            method: 'PATCH',
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (response.ok) {
            showToast('Project activated successfully', 'success');
            loadProjects();
        } else {
            showToast('Cannot activate project. Ensure it has at least one task.', 'error');
        }
    } catch (e) {
        showToast('Error activating project', 'error');
    }
}

async function createTask(projectId) {
    const title = prompt('Task Title:');
    if (!title) return;

    try {
        const response = await fetch(`${API_URL}/projects/${projectId}/tasks`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ title })
        });

        if (response.ok) {
            showToast('Task added successfully', 'success');
            loadProjects();
        } else {
            showToast('Failed to add task', 'error');
        }
    } catch (e) {
        showToast('Error adding task', 'error');
    }
}

async function completeTask(taskId) {
    try {
        const response = await fetch(`${API_URL}/tasks/${taskId}/complete`, {
            method: 'PATCH',
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (response.ok) {
            showToast('Task completed successfully', 'success');
            loadProjects();
        } else {
            showToast('Failed to complete task', 'error');
        }
    } catch (e) {
        showToast('Error completing task', 'error');
    }
}

async function deleteProject(id) {
    if (!confirm('Are you sure you want to delete this project?')) return;

    try {
        const response = await fetch(`${API_URL}/projects/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (response.ok) {
            showToast('Project deleted', 'success');
            loadProjects();
        } else {
            showToast('Failed to delete project', 'error');
        }
    } catch (e) {
        showToast('Error deleting project', 'error');
    }
}

async function deleteTask(id) {
    if (!confirm('Delete task?')) return;

    try {
        const response = await fetch(`${API_URL}/tasks/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });
        if (response.ok) {
            showToast('Task deleted', 'success');
            loadProjects();
        } else {
            showToast('Failed to delete task', 'error');
        }
    } catch (e) {
        showToast('Error deleting task', 'error');
    }
}

function renderProjects(projects) {
    const container = document.getElementById('projects-list');
    container.innerHTML = projects.map(p => `
        <div class="project-card">
            <div class="project-header">
                <h3>${p.name}</h3>
                <div style="display: flex; gap: 5px; align-items: center;">
                    <span class="status-badge status-${p.status}">${p.status}</span>
                    <button onclick="deleteProject('${p.id}')" style="background: none; border: none; color: #ff4444; cursor: pointer; font-size: 1.2rem;">🗑️</button>
                </div>
            </div>
            
            <div class="task-list">
                <h4>Tasks</h4>
                ${p.tasks && p.tasks.length > 0 ? p.tasks.map(t => `
                    <div class="task-item">
                        <span class="${t.completed ? 'task-completed' : ''}">${t.title}</span>
                        <div class="task-actions" style="margin-left:auto; display: flex; gap: 5px;">
                            ${!t.completed ? `<button onclick="completeTask('${t.id}')" style="padding: 2px 5px; font-size: 0.8rem;">Complete</button>` : ''}
                            <button onclick="deleteTask('${t.id}')" style="background: none; border: none; color: #ff4444; cursor: pointer;">✕</button>
                        </div>
                    </div>
                `).join('') : '<div class="empty-tasks">No tasks yet. Add one above!</div>'}
            </div>

            <div class="actions">
                <button onclick="createTask('${p.id}')">Add Task</button>
                ${p.status === 'DRAFT' ?
            `<button onclick="activateProject('${p.id}')">Activate</button>` : ''}
            </div>
        </div>
    `).join('');

    if (projects.length === 0) {
        container.innerHTML = `
            <div class="empty-state">
                <h3>🚀 Welcome!</h3>
                <p>You don't have any projects yet.</p>
                <p>Start by typing a name above and clicking <b>"Create Project"</b>.</p>
            </div>
        `;
    }
}

function showError(msg, color = 'red') {
    const el = document.getElementById('auth-message');
    if (el) {
        el.textContent = msg;
        el.style.color = color;
    }
}
