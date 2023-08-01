package com.vigacat.security.filterRequestVigacat.component.request;

import com.vigacat.security.filterRequestVigacat.dto.UserDto;

public interface UserRequest {
    UserDto getUserByUsernameAndApp(String username, Long appId);
}
