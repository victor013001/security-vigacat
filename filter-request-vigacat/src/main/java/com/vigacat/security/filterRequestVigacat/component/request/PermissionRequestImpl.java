package com.vigacat.security.filterRequestVigacat.component.request;

import com.vigacat.security.filterRequestVigacat.dto.PermissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionRequestImpl implements PermissionRequest{

    private final WebClient webClient;

    @Override
    public List<PermissionDto> getPermissionsByRoleIds(List<Long> roleIds) {
        return webClient.post()
                .uri("/auth/permissions")
                .body(Mono.just(roleIds),List.class)
                .retrieve()
                .bodyToFlux(PermissionDto.class)
                .collectList()
                .block();
    }
}
