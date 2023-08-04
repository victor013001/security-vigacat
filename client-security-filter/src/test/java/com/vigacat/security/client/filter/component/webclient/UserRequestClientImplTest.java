package com.vigacat.security.client.filter.component.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigacat.security.client.filter.dto.PermissionDto;
import com.vigacat.security.client.filter.dto.RoleDto;
import com.vigacat.security.client.filter.dto.UserRolesPermissionsDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserRequestClientImplTest {

    @Test
    public void getUserRolesPermissionsByToken() throws JsonProcessingException {
        String permissionRead = "Read";
        String username = "User";

        List<RoleDto> userRoleDtos = List.of(
                RoleDto.builder()
                        .id(1L)
                        .build()
        );
        PermissionDto permissionDtoRead = PermissionDto.builder()
                .permission(permissionRead)
                .build();
        List<PermissionDto> userPermissionDtos = List.of(
                permissionDtoRead
        );

        UserRolesPermissionsDto userRolesPermissionsDto = UserRolesPermissionsDto.builder()
                .name(username)
                .roles(userRoleDtos)
                .permissions(userPermissionDtos)
                .build();

        String jsonWebClientResponse = new ObjectMapper().writeValueAsString(userRolesPermissionsDto);

        RouterFunction function = RouterFunctions.route(
                RequestPredicates.GET("/user/filter"),
                request -> ServerResponse.ok()
                        .bodyValue(jsonWebClientResponse)
        );

        WebTestClient
                .bindToRouterFunction(function)
                .build().get().uri("/user/filter")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .toString()
                .contains(jsonWebClientResponse);
    }


}
