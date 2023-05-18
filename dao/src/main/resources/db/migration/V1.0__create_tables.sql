CREATE TABLE Users
(
    userID bigint(20) NOT NULL,
    userEmail varchar(64) NOT NULL,
    userName varchar(32) NOT NULL,
    password varchar(64) NOT NULL,
    createdAt timestamp NOT NULL,
    updatedAt timestamp NOT NULL,
    PRIMARY KEY (UserID)
);

CREATE TABLE Apps
(
    appID bigint(20) NOT NULL,
    appName varchar(32) NOT NULL,
    PRIMARY KEY (appID)
);

CREATE TABLE Roles
(
    rolID bigint(20) NOT NULL,
    rolName varchar(32) NOT NULL,
    createdAt timestamp NOT NULL,
    updatedAt timestamp NOT NULL,
    appID bigint(20) NOT NULL,
    PRIMARY KEY (RolID),
    FOREIGN KEY (appID) REFERENCES Apps(appID)
);

CREATE TABLE Permissions
(
    permissionID bigint(20) NOT NULL,
    permission varchar(32) NOT NULL,
    createdAt timestamp NOT NULL,
    updatedAt timestamp NOT NULL,
    PRIMARY KEY (permissionID)
);

CREATE TABLE UserRoles
(
    userID bigint(20) NOT NULL,
    rolID bigint(20) NOT NULL,
    userRoleId bigint(20) NOT NULL,
    createdAt timestamp NOT NULL,
    updatedAt timestamp NOT NULL,
    PRIMARY KEY (userID, rolID, userRoleId),
    FOREIGN KEY (userID) REFERENCES Users(userID),
    FOREIGN KEY (rolID) REFERENCES Roles(rolID)
);

CREATE TABLE RolePermissions
(
    rolID bigint(20) NOT NULL,
    permissionID bigint(20) NOT NULL,
    rolePermissionId bigint(20) NOT NULL,
    createdAt timestamp NOT NULL,
    updatedAt timestamp NOT NULL,
    PRIMARY KEY (rolID, permissionID, rolePermissionId),
    FOREIGN KEY (rolID) REFERENCES Roles(rolID),
    FOREIGN KEY (permissionID) REFERENCES Permissions(permissionID)
);

