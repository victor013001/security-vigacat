SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE security_vigacat.Users
    MODIFY userId bigint(20) NOT NULL AUTO_INCREMENT,
    MODIFY createdAt timestamp DEFAULT CURRENT_TIMESTAMP,
    MODIFY updatedAt timestamp,
    ADD COLUMN createdBy bigint(20) NOT NULL,
    ADD COLUMN updatedBy bigint(20) DEFAULT NULL;

ALTER TABLE security_vigacat.RolePermissions DROP FOREIGN KEY rolepermissions_ibfk_1;

ALTER TABLE security_vigacat.UserRoles DROP FOREIGN KEY userroles_ibfk_2;

ALTER TABLE security_vigacat.UserRoles DROP FOREIGN KEY userroles_ibfk_1;

ALTER TABLE security_vigacat.Roles
    DROP PRIMARY KEY,
    CHANGE rolID roleId bigint(20) NOT NULL AUTO_INCREMENT,
    MODIFY createdAt timestamp DEFAULT CURRENT_TIMESTAMP,
    MODIFY updatedAt timestamp DEFAULT NULL,
    ADD COLUMN createdBy bigint(20) NOT NULL,
    ADD COLUMN updatedBy bigint(20),
    ADD PRIMARY KEY (roleId);

ALTER TABLE security_vigacat.UserRoles
    DROP PRIMARY KEY,
    MODIFY userRoleId bigint(20) NOT NULL AUTO_INCREMENT,
    RENAME COLUMN rolId TO roleId,
    MODIFY createdAt timestamp DEFAULT CURRENT_TIMESTAMP,
    MODIFY updatedAt timestamp,
    ADD COLUMN createdBy bigint(20) NOT NULL,
    ADD COLUMN updatedBy bigint(20) DEFAULT NULL,
    ADD PRIMARY KEY (userRoleId),
    ADD FOREIGN KEY (roleId) REFERENCES Roles (roleId);

ALTER TABLE security_vigacat.Apps
    MODIFY appId bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE security_vigacat.RolePermissions
    DROP PRIMARY KEY,
    MODIFY rolePermissionId bigint(20) NOT NULL AUTO_INCREMENT,
    RENAME COLUMN rolId TO roleId,
    MODIFY createdAt timestamp DEFAULT CURRENT_TIMESTAMP,
    MODIFY updatedAt timestamp,
    ADD COLUMN createdBy bigint(20) NOT NULL,
    ADD COLUMN updatedBy bigint(20) DEFAULT NULL,
    ADD PRIMARY KEY (rolePermissionId),
    ADD FOREIGN KEY (roleId) REFERENCES Roles (roleId);

ALTER TABLE security_vigacat.Permissions
    MODIFY permissionId bigint(20) NOT NULL AUTO_INCREMENT,
    MODIFY createdAt timestamp DEFAULT CURRENT_TIMESTAMP,
    MODIFY updatedAt timestamp,
    ADD COLUMN createdBy bigint(20) NOT NULL,
    ADD COLUMN updatedBy bigint(20) DEFAULT NULL;

SET FOREIGN_KEY_CHECKS=1;