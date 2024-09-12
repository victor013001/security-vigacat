INSERT INTO app (id, name) VALUES (3, 'Review');

INSERT INTO permission (id, permission) VALUES (6, 'REV_CREATE_REVIEW');
INSERT INTO permission (id, permission) VALUES (7, 'REV_UPDATE_REVIEW');
INSERT INTO permission (id, permission) VALUES (8, 'REV_DELETE_REVIEW');

INSERT INTO `role` (name, created_at, created_by, app_id) VALUES ('Review User', now(), 'SYSTEM', 3);

INSERT INTO role_permission (role_id, permission_id)
VALUES
    ((SELECT id FROM `role` WHERE name = 'Review User' AND app_id = 3), 6),
    ((SELECT id FROM `role` WHERE name = 'Review User' AND app_id = 3), 7),
    ((SELECT id FROM `role` WHERE name = 'Review User' AND app_id = 3), 8);

INSERT INTO user_role (user_id, rol_id)
VALUES (1, (SELECT id FROM `role` WHERE name = 'Review User' AND app_id = 3));

-- REVERT
-- DELETE FROM app WHERE id = 3;
-- DELETE FROM permission WHERE id = 6;
-- DELETE FROM permission WHERE id = 7;
-- DELETE FROM permission WHERE id = 8;
-- DELETE FROM `role` WHERE name = 'Review User' AND app_id = 2;
-- DELETE FROM role_permission WHERE role_id = (SELECT id FROM `role` WHERE name = 'Review User' AND app_id = 3) AND permission_id = 6;
-- DELETE FROM role_permission WHERE role_id = (SELECT id FROM `role` WHERE name = 'Review User' AND app_id = 3) AND permission_id = 7;
-- DELETE FROM role_permission WHERE role_id = (SELECT id FROM `role` WHERE name = 'Review User' AND app_id = 3) AND permission_id = 8;
-- DELETE FROM user_role WHERE user_id = 1 AND (SELECT id FROM `role` WHERE name = 'Review User' AND app_id = 3);
-- DELETE FROM flyway_schema_history WHERE version = '1.8';