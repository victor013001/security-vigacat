package com.vigacat.security.persistence.component;

import com.vigacat.security.persistence.dto.RoleDto;

import java.util.Optional;

public interface RolePersistence {

    Optional<RoleDto> getRoleByNameAndAppId(String roleName, Long appId);

    RoleDto saveNewRole(RoleDto roleDto, String usernameToken, Long appId);

}
