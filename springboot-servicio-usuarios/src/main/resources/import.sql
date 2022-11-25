INSERT INTO users (username, password, enabled, name, lastname, email) VALUES ('yanpieer', '$2a$10$Ub25f7E7ng5aSNRIy2j7.O8uDcroT8lKNZR8xmdbfIhOMEwQQcapi', true, 'Yanpieer', 'Romero', 'yanpieercode@gmail.com');
INSERT INTO users (username, password, enabled, name, lastname, email) VALUES ('admin', '$2a$10$mRjyMmlV2jQAtoIEspoAFuiYQcsk76.dUwjeg0WbYsUcepHOo2W0q', true, 'Josue', 'Salazar', 'siryancode@gmail.com');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);