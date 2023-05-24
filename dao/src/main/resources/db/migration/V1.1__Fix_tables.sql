DROP TABLE IF EXISTS RolePermissions;
DROP TABLE IF EXISTS UserRoles;
DROP TABLE IF EXISTS Permissions;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS Apps;
DROP TABLE IF EXISTS Users;

CREATE TABLE user
(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    email varchar(64) NOT NULL,
    name varchar(32) NOT NULL,
    password varchar(512) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp DEFAULT NULL,
    created_by varchar(32) NOT NULL,
    updated_by varchar(32) default null,
    PRIMARY KEY (id)
);

CREATE TABLE app
(
    id bigint(20) NOT NULL,
    name varchar(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE role
(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(32) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp DEFAULT NULL,
    created_by varchar(32) NOT NULL,
    updated_by varchar(32) default null,
    app_id bigint(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (app_id) REFERENCES app(id)
);

CREATE TABLE permission
(
    id bigint(20) NOT NULL,
    permission varchar(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    user_id bigint(20) NOT NULL,
    rol_id bigint(20) NOT NULL,
    PRIMARY KEY (user_id, rol_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (rol_id) REFERENCES role(id)
);

CREATE TABLE role_permission
(
    role_id bigint(20) NOT NULL,
    permission_id bigint(20) NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);
