-- 1. Ver Usuarios (Para obtener tu ID)
SELECT 
    BIN_TO_UUID(id) as user_id, 
    username, 
    email 
FROM users;

-- 2. Ver Proyectos (Verificar que owner_id coincida con tu user_id)
SELECT 
    BIN_TO_UUID(id) as project_id, 
    name, 
    status, 
    BIN_TO_UUID(owner_id) as owner_id 
FROM projects;

-- 3. Ver Tareas (Verificar que project_id coincida con tu project_id)
SELECT 
    BIN_TO_UUID(id) as task_id, 
    title, 
    completed, 
    BIN_TO_UUID(project_id) as project_id 
FROM tasks;
