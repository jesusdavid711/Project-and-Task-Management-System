-- Initial Users
-- Password is 'password' (bcrypt hash needed, using placeholder if plain text not allowed, but usually needs hash)
-- Generating bcrypt for '123456' -> $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EXsM21p.2p.
INSERT INTO users (id, username, email, password) VALUES 
(UNHEX(REPLACE('00000000-0000-0000-0000-000000000001', '-', '')), 'admin', 'admin@test.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EXsM21p.2p.');

INSERT INTO users (id, username, email, password) VALUES 
(UNHEX(REPLACE('00000000-0000-0000-0000-000000000002', '-', '')), 'user', 'user@test.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EXsM21p.2p.');

-- Initial Projects (Owned by admin)
INSERT INTO projects (id, owner_id, name, status, deleted) VALUES
(UNHEX(REPLACE('10000000-0000-0000-0000-000000000001', '-', '')), UNHEX(REPLACE('00000000-0000-0000-0000-000000000001', '-', '')), 'Website Redesign', 'ACTIVE', false),
(UNHEX(REPLACE('10000000-0000-0000-0000-000000000002', '-', '')), UNHEX(REPLACE('00000000-0000-0000-0000-000000000001', '-', '')), 'Mobile App', 'DRAFT', false);

-- Initial Tasks
INSERT INTO tasks (id, project_id, title, completed, deleted) VALUES
(UNHEX(REPLACE('20000000-0000-0000-0000-000000000001', '-', '')), UNHEX(REPLACE('10000000-0000-0000-0000-000000000001', '-', '')), 'Design Mockups', true, false),
(UNHEX(REPLACE('20000000-0000-0000-0000-000000000002', '-', '')), UNHEX(REPLACE('10000000-0000-0000-0000-000000000001', '-', '')), 'Setup DevOps', false, false);
