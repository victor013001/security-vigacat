package com.vigacat.security.service.component;

import com.vigacat.security.persistence.dto.PermissionDto;

import java.util.List;

public interface PermissionService {

    List<PermissionDto> getPermissionsByNames(List<String> permissionNames);
}
