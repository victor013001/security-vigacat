package com.vigacat.security.persistence.component;

import com.vigacat.security.persistence.dto.PermissionDto;

import java.util.List;

public interface PermissionPersistence {
    List<PermissionDto> getPermissionsByRoleIds(List<Long> roleIds);

    List<PermissionDto> getPermissionsByNames(List<String> permissionNames);

}
