INSERT INTO permission(id, permission) VALUES (5, 'CAT_CREATE_GAMES');

INSERT INTO role_permission (role_id, permission_id)
VALUES ((SELECT id FROM `role` WHERE name = 'Catalogue User' AND app_id = 2), 5);

-- REVERT
-- DELETE FROM permission WHERE id = 5;
-- DELETE FROM role_permission WHERE role_id = (SELECT id FROM `role` WHERE name = 'Catalogue User' AND app_id = 2) AND permission_id = 5;
-- DELETE FROM flyway_schema_history WHERE version = '1.7';