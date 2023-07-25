package com.vigacat.security.persistence.component;

import com.vigacat.security.persistence.dto.RoleDto;

public interface RolePersistence {

    RoleDto saveNewRole(RoleDto roleDto, Long appId, String usernameAuthenticated);

    boolean roleNameExist(String roleName, Long appId);

}
