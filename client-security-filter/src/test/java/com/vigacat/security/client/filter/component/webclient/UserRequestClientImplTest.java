package com.vigacat.security.client.filter.component.webclient;

import com.vigacat.security.client.filter.component.webclient.constant.ClientHeader;
import com.vigacat.security.client.filter.dto.PermissionDto;
import com.vigacat.security.client.filter.dto.RoleDto;
import com.vigacat.security.client.filter.dto.UserRolesPermissionsDto;
import org.assertj.core.api.Assertions;
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

    private WebTestClient webTestClient;

    @Test
    public void getUserRolesPermissionsByToken() {
        String permissionRead = "Read";
        String username = "User";
        String appName = "Catalogue";
        String userToken = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
        String roleUser = "User";

        List<RoleDto> userRoleDtos = List.of(
                RoleDto.builder()
                        .id(1L)
                        .name(roleUser)
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

        RouterFunction function = RouterFunctions.route(
                RequestPredicates.GET("/user/filter"),
                request -> ServerResponse.ok()
                        .bodyValue(userRolesPermissionsDto)
        );

        webTestClient = WebTestClient
                .bindToRouterFunction(function)
                .build();

        UserRolesPermissionsDto userRolesPermissionsResponse = webTestClient.get()
                .uri("/user/filter")
                .header(ClientHeader.APPLICATION_NAME, appName)
                .header(ClientHeader.AUTHORIZATION_NAME, userToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserRolesPermissionsDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(userRolesPermissionsResponse)
                .hasFieldOrPropertyWithValue("name", username);

        Assertions.assertThat(userRolesPermissionsResponse.getRoles())
                .extracting(RoleDto::getName)
                .contains(roleUser);

        Assertions.assertThat(userRolesPermissionsResponse.getPermissions())
                .extracting(PermissionDto::getPermission)
                .contains(permissionRead);
    }


}
