ALTER TABLE `user` ADD CONSTRAINT user_email_UN UNIQUE KEY (email);
ALTER TABLE permission ADD CONSTRAINT permission_UN UNIQUE KEY (permission);
ALTER TABLE `role` ADD CONSTRAINT role_app_UN UNIQUE KEY (name, app_id);
INSERT INTO permission (id, permission) VALUES(2, 'SEC_CREATE_ROLES');

-- REVERT
-- ALTER TABLE `user` DROP CONSTRAINT user_email_UN;
-- ALTER TABLE permission DROP CONSTRAINT permission_UN;
-- ALTER TABLE `role` DROP CONSTRAINT role_app_UN;
-- DELETE FROM role_permission WHERE permission_id = 2;
-- DELETE FROM permission WHERE id = 2;
-- DELETE FROM flyway_schema_history WHERE version = '1.4';


