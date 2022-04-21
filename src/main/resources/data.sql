# INSERT INTO EMPLOYEE(ID, CAPACITY, EMAIL, FIRST_NAME, GRADE, LAST_NAME, PASSWORD, USERNAME)
# VALUES (1, 100, 'employee@app.com', 'Pracownik', 'REGULAR', 'Test', 'password', 'user');
# INSERT INTO EMPLOYEE(ID, CAPACITY, EMAIL, FIRST_NAME, GRADE, LAST_NAME, PASSWORD, USERNAME)
# VALUES (2, 100, 'manager@app.com', 'Manager', 'SENIOR', 'Test', 'password', 'moderator');
# INSERT INTO EMPLOYEE(ID, CAPACITY, EMAIL, FIRST_NAME, GRADE, LAST_NAME, PASSWORD, USERNAME)
# VALUES (3, 100, 'admin@app.com', 'Admin', 'SPECIALIST', 'Test', 'password', 'admin');
#
# INSERT INTO project(id, description, name, project_status, owner_id)
# VALUES (1, 'To jest testowy opis testowego PIERWSZEGO projektu', 'Testowy projekt - pierwszy', 'CLOSED', 2);
# INSERT INTO project(id, description, name, project_status, owner_id)
# VALUES (2, 'To jest testowy opis testowego DRUGIEGO projektu', 'Testowy projekt - drugi', 'OPEN', 2);
#
# INSERT INTO skill(id, skill_category, skill_level, skill_name)
# VALUES (1,'PROGRAMMING_LANGUAGES', 'BEGINNER', 'PROGRAMMING');
# INSERT INTO skill(id, skill_category, skill_level, skill_name)
# VALUES (2,'PROGRAMMING_LANGUAGES', 'INTERMEDIATE', 'PROGRAMMING');
# INSERT INTO skill(id, skill_category, skill_level, skill_name)
# VALUES (3,'PROGRAMMING_LANGUAGES', 'ADVANCED', 'PROGRAMMING');
#
# INSERT INTO task(id, capacity, grade, role, task_status, employee_id, project_id)
# VALUES (1, 100, 'REGULAR', 'ROLE_EMPLOYEE', 'OPEN', 1, 2);
# INSERT INTO task(id, capacity, grade, role, task_status, employee_id, project_id)
# VALUES (2, 100, 'REGULAR', 'ROLE_PM', 'OPEN', 2, 1);
#
# INSERT INTO task_request(id, task_request_status, employee_id, task_id)
# VALUES (1, 'WAITING_FOR_APPROVE', 1, 2);
# INSERT INTO task_request(id, task_request_status, employee_id, task_id)
# VALUES (2, 'ACCEPTED', 1, 1);
# INSERT INTO task_request(id, task_request_status, employee_id, task_id)
# VALUES (3, 'ACCEPTED', 2, 2);
# INSERT INTO task_request(id, task_request_status, employee_id, task_id)
# VALUES (4, 'REJECTED', 2, 1);
#
# INSERT INTO employee_roles(employee_id, roles) VALUES (1, 'EMPLOYEE');
# INSERT INTO employee_roles(employee_id, roles) VALUES (2, 'EMPLOYEE');
# INSERT INTO employee_roles(employee_id, roles) VALUES (2, 'PM');
# INSERT INTO employee_roles(employee_id, roles) VALUES (3, 'ADMIN');
#
# INSERT INTO employee_skill(employee_id, skill_id) VALUES (1, 2);
#
# COMMIT;