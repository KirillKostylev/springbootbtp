-- CREATE SCHEMA IF NOT EXISTS reportingdb_schema;
-- CREATE DATABASE reportingdb;
-- USE reportingdb;

CREATE TABLE employee
(
    id   BIGSERIAL PRIMARY KEY,
    name CHARACTER VARYING(50)
);

CREATE TABLE project
(
    id   BIGSERIAL PRIMARY KEY,
    name CHARACTER VARYING(50)
);

CREATE TABLE project_employee
(
    project_id  BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    role        CHARACTER VARYING(50),
    PRIMARY KEY (project_id, employee_id)
);

CREATE TABLE report
(
    id          BIGSERIAL PRIMARY KEY,
    project_id  BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    description TEXT,
    created_at  TIMESTAMP
);

INSERT INTO employee (name)
VALUES ('Andrei');
INSERT INTO employee (name)
VALUES ('Stas');
INSERT INTO employee (name)
VALUES ('Marharita');
INSERT INTO employee (name)
VALUES ('Kristina');

INSERT INTO project (name)
VALUES ('Java');
INSERT INTO project (name)
VALUES ('C#');
INSERT INTO project (name)
VALUES ('GO');
INSERT INTO project (name)
VALUES ('C++');

INSERT INTO project_employee (project_id, employee_id, role)
VALUES ((SELECT id FROM project WHERE name = 'Java'),
        (SELECT id FROM employee WHERE name = 'Andrei'),
        'Developer');
INSERT INTO project_employee (project_id, employee_id, role)
VALUES ((SELECT id FROM project WHERE name = 'C++'),
        (SELECT id FROM employee WHERE name = 'Andrei'),
        'Developer');
INSERT INTO project_employee (project_id, employee_id, role)
VALUES ((SELECT id FROM project WHERE name = 'GO'),
        (SELECT id FROM employee WHERE name = 'Andrei'),
        'Developer');
INSERT INTO project_employee (project_id, employee_id, role)
VALUES ((SELECT id FROM project WHERE name = 'C#'),
        (SELECT id FROM employee WHERE name = 'Stas'),
        'Developer');
INSERT INTO project_employee (project_id, employee_id, role)
VALUES ((SELECT id FROM project WHERE name = 'Java'),
        (SELECT id FROM employee WHERE name = 'Stas'),
        'Developer');
INSERT INTO project_employee (project_id, employee_id, role)
VALUES ((SELECT id FROM project WHERE name = 'GO'),
        (SELECT id FROM employee WHERE name = 'Marharita'),
        'Developer');
INSERT INTO project_employee (project_id, employee_id, role)
VALUES ((SELECT id FROM project WHERE name = 'C++'),
        (SELECT id FROM employee WHERE name = 'Kristina'),
        'Developer');

INSERT INTO report (project_id, employee_id, description, created_at)
VALUES ((SELECT id FROM project WHERE name = 'Java'),
        (SELECT id FROM employee WHERE name = 'Andrei'),
        'Andrei Description', current_timestamp);
INSERT INTO report (project_id, employee_id, description, created_at)
VALUES ((SELECT id FROM project WHERE name = 'C#'),
        (SELECT id FROM employee WHERE name = 'Stas'),
        'Stas Description', current_timestamp);
INSERT INTO report (project_id, employee_id, description, created_at)
VALUES ((SELECT id FROM project WHERE name = 'GO'),
        (SELECT id FROM employee WHERE name = 'Marharita'),
        'Marharita Description', current_timestamp);
INSERT INTO report (project_id, employee_id, description, created_at)
VALUES ((SELECT id FROM project WHERE name = 'C++'),
        (SELECT id FROM employee WHERE name = 'Kristina'),
        'Kristina Description', current_timestamp);