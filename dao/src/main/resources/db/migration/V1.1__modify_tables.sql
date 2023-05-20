SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE RolePermissions DROP FOREIGN KEY rolepermissions_ibfk_1;

ALTER TABLE UserRoles DROP FOREIGN KEY userroles_ibfk_2;

ALTER TABLE UserRoles DROP FOREIGN KEY userroles_ibfk_1;

ALTER TABLE Roles DROP PRIMARY KEY,
    CHANGE rolID roleID bigint(20) NOT NULL AUTO_INCREMENT,
    ADD PRIMARY KEY (roleID);

ALTER TABLE UserRoles
    DROP PRIMARY KEY,
    CHANGE userRoleId userRoleID bigint(20) NOT NULL AUTO_INCREMENT,
    RENAME COLUMN rolID TO roleID,
    MODIFY createdAt timestamp DEFAULT CURRENT_TIMESTAMP,
    MODIFY updatedAt timestamp DEFAULT NULL,
    ADD COLUMN createdBy bigint(20) NOT NULL,
    ADD COLUMN updatedBy bigint(20) DEFAULT NULL,
    ADD PRIMARY KEY (userRoleID),
    ADD FOREIGN KEY (roleID) REFERENCES Roles (roleID);

ALTER TABLE RolePermissions
    DROP PRIMARY KEY,
    CHANGE rolePermissionId rolePermissionID bigint(20) NOT NULL AUTO_INCREMENT,
    RENAME COLUMN rolId TO roleId,
    MODIFY createdAt timestamp DEFAULT CURRENT_TIMESTAMP,
    MODIFY updatedAt timestamp DEFAULT NULL,
    ADD COLUMN createdBy bigint(20) NOT NULL,
    ADD COLUMN updatedBy bigint(20) DEFAULT NULL,
    ADD PRIMARY KEY (rolePermissionID),
    ADD FOREIGN KEY (roleID) REFERENCES Roles (roleID);

ALTER TABLE Roles
    CHANGE rolName roleName varchar(32) NOT NULL UNIQUE,
    MODIFY createdAt timestamp DEFAULT CURRENT_TIMESTAMP,
    MODIFY updatedAt timestamp DEFAULT NULL,
    ADD COLUMN createdBy bigint(20) NOT NULL,
    ADD COLUMN updatedBy bigint(20),
    ADD COLUMN userRoleID bigint(20),
    ADD FOREIGN KEY (userRoleID) REFERENCES UserRoles (userRoleID),
    ADD COLUMN rolePermissionID bigint(20),
    ADD FOREIGN KEY (rolePermissionID) REFERENCES RolePermissions (rolePermissionID);

ALTER TABLE Users
    MODIFY userID bigint(20) NOT NULL AUTO_INCREMENT,
    MODIFY userEmail varchar(64) NOT NULL UNIQUE,
    MODIFY userName varchar(32) NOT NULL UNIQUE,
    MODIFY createdAt timestamp DEFAULT CURRENT_TIMESTAMP,
    MODIFY updatedAt timestamp DEFAULT NULL,
    ADD COLUMN createdBy bigint(20) NOT NULL,
    ADD COLUMN updatedBy bigint(20) DEFAULT NULL,
    ADD COLUMN userRoleID bigint(20),
    ADD FOREIGN KEY (userRoleID) REFERENCES UserRoles (userRoleID);


ALTER TABLE Apps
    MODIFY appID bigint(20) NOT NULL AUTO_INCREMENT,
    ADD COLUMN roleId bigint(20),
    ADD FOREIGN KEY (roleId) REFERENCES Roles (roleId);

ALTER TABLE Permissions
    MODIFY permissionID bigint(20) NOT NULL AUTO_INCREMENT,
    MODIFY permission varchar(32) NOT NULL UNIQUE,
    MODIFY createdAt timestamp DEFAULT CURRENT_TIMESTAMP,
    MODIFY updatedAt timestamp DEFAULT NULL,
    ADD COLUMN createdBy bigint(20) NOT NULL,
    ADD COLUMN updatedBy bigint(20) DEFAULT NULL,
    ADD COLUMN rolePermissionID bigint(20),
    ADD FOREIGN KEY (rolePermissionID) REFERENCES RolePermissions (rolePermissionID);

SET FOREIGN_KEY_CHECKS=1;