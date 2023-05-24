ALTER TABLE `user` ADD CONSTRAINT user_UN UNIQUE KEY (name);

INSERT INTO app
(id, name)
VALUES(1, 'Security');

INSERT INTO permission
(id, permission)
VALUES(1, 'SEC_QUERY_USERS');

INSERT INTO `role`
(name, created_at, created_by, app_id)
VALUES('Administrator', now(), 'SYSTEM', 1);

INSERT INTO role_permission
(role_id, permission_id)
VALUES((SELECT id FROM role WHERE name = 'Administrator' AND app_id = 1), 1);

INSERT INTO `user`
(email, name, password, created_at, created_by)
VALUES('camilot22@gmail.com', 'sergio.torres', 'dummy_psw', now(), 'SYSTEM');

INSERT INTO user_role
(user_id, rol_id)
VALUES((SELECT id FROM user WHERE name = 'sergio.torres'), (SELECT id FROM role WHERE name = 'Administrator' AND app_id = 1));

-- REVERT
-- DELETE FROM user_role WHERE user_id > 0;
-- DELETE FROM user WHERE id > 0;
-- DELETE FROM role_permission WHERE role_id > 0;
-- DELETE FROM role WHERE id > 0;
-- DELETE FROM permission WHERE id > 0;
-- DELETE FROM app WHERE id > 0;
-- ALTER TABLE user DROP CONSTRAINT user_UN;
-- DELETE FROM flyway_schema_history WHERE version = '1.2';

