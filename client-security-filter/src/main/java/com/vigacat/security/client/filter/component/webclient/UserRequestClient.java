package com.vigacat.security.client.filter.component.webclient;

import com.vigacat.security.client.filter.dto.UserRolesPermissionsDto;

public interface UserRequestClient {
    UserRolesPermissionsDto getUserRolesPermissionsByToken(String token);
}
