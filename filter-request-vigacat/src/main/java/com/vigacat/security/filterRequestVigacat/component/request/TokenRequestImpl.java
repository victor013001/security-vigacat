package com.vigacat.security.filterRequestVigacat.component.request;

import com.vigacat.security.filterRequestVigacat.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenRequestImpl implements TokenRequest {

    private final WebClient webClient;

    @Override
    public Optional<TokenDto> getToken(String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/auth/token")
                        .queryParam("value", token)
                        .build())
                .retrieve()
                .bodyToMono(TokenDto.class)
                .blockOptional();
    }
}
