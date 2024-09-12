package com.vigacat.security.client.filter.component.webclient;

import com.vigacat.security.client.filter.component.webclient.constant.ClientHeader;
import com.vigacat.security.client.filter.dto.UserRolesPermissionsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UserRequestClientImpl implements UserRequestClient {

    private final WebClient webClient;

    @Value("${vigacat.app.name}")
    private String appName;

    @Override
    public UserRolesPermissionsDto getUserRolesPermissionsByToken(String token) {
        return webClient.get()
                .uri("/user/filter")
                .header(ClientHeader.APPLICATION_NAME, appName)
                .header(ClientHeader.AUTHORIZATION_NAME, token)
                .retrieve()
                .bodyToMono(UserRolesPermissionsDto.class)
                .block();
    }
}
