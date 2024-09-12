INSERT INTO roles (role_name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER')
ON CONFLICT DO NOTHING;

INSERT INTO users (user_id, username, password)
VALUES (1, 'admin', '$2a$10$.B13zmj5///5cEXMN.AGDO4YMY1lxeeAn4IzVPwbJ.MVEeKecG/p6')
ON CONFLICT DO NOTHING;

INSERT INTO users_roles (id, user_id, role_id)
VALUES (1, 1, 1)
ON CONFLICT DO NOTHING;