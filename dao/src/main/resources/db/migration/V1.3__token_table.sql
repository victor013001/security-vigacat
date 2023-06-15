ALTER TABLE `user` ADD CONSTRAINT user_name_UN UNIQUE KEY (name);

CREATE TABLE token (
    username varchar(32),
    token varchar(64) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    PRIMARY KEY (username)
);

-- REVERT
-- ALTER TABLE `user` DROP CONSTRAINT user_name_UN;
-- DROP TABLE token;
-- DELETE FROM flyway_schema_history WHERE version = '1.3';

