package com.vigacat.security.filterRequestVigacat.component.request;

import com.vigacat.security.filterRequestVigacat.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UserRequestImpl implements UserRequest {

    private final WebClient webClient;

    @Override
    public UserDto getUserByUsernameAndApp(String username, Long appId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/auth/usernameAndApp")
                        .queryParam("username", username)
                        .queryParam("app_id", appId)
                        .build())
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }
}
