ALTER TABLE `user` ADD CONSTRAINT user_email_UN UNIQUE KEY (email);
ALTER TABLE `role` ADD CONSTRAINT role_UN UNIQUE KEY (name);
ALTER TABLE permission ADD CONSTRAINT permission_UN UNIQUE KEY (permission);

-- REVERT
-- ALTER TABLE `user` DROP CONSTRAINT user_email_UN;
-- ALTER TABLE `role` DROP CONSTRAINT role_UN;
-- ALTER TABLE permission DROP CONSTRAINT permission_UN;
-- DELETE FROM flyway_schema_history WHERE version = '1.4';


