ALTER TABLE app ADD CONSTRAINT app_UN UNIQUE KEY (name);

INSERT INTO app (id, name) VALUES (2, 'Catalogue');

INSERT INTO permission (id, permission) VALUES (4,'CAT_QUERY_GAMES');

INSERT INTO `role` (name, created_at, created_by, app_id) VALUES ('Catalogue User', now(), 'SYSTEM', 2);

INSERT INTO role_permission (role_id, permission_id)
VALUES ((SELECT id FROM `role` WHERE name = 'Catalogue User' AND app_id = 2), 4);

INSERT INTO user_role (user_id, rol_id)
VALUES (1,(SELECT id FROM `role` WHERE name = 'Catalogue User' AND app_id = 2));

-- REVERT
-- ALTER TABLE app DROP CONSTRAINT app_UN;
-- DELETE FROM user_role WHERE user_id = 1 AND rol_id = (SELECT id FROM `role` WHERE name = 'Catalogue User' AND app_id = 2);
-- DELETE FROM role_permission WHERE role_id = (SELECT id FROM `role` WHERE name = 'Catalogue User' AND app_id = 2) AND permission_id = 4;
-- DELETE FROM `role` WHERE name = 'Catalogue User' AND app_id = 2;
-- DELETE FROM permission WHERE id = 4;
-- DELETE FROM app WHERE id = 2;
-- DELETE FROM flyway_schema_history WHERE version = '1.6';