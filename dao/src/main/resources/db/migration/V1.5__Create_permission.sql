INSERT INTO permission(id, permission) VALUES (3, 'SEC_CREATE_USERS');

-- REVERT
-- DELETE FROM permission WHERE id = 3;
-- DELETE FROM flyway_schema_history WHERE version = '1.5';