package com.vigacat.security.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigacat.security.persistence.dto.PermissionDto;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.service.component.RoleService;
import com.vigacat.security.web.dto.RoleRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void createNewRole() throws Exception {

        String roleNameUserDto = "User";
        String adminAuthorization = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";

        Long appId = 1L;

        List<String> rolePermissionsDto = List.of("Read", "Create");

        RoleRequest roleUserRequest = RoleRequest.builder()
                .permissions(rolePermissionsDto)
                .name(roleNameUserDto)
                .build();

        String createRolUserRequest = new ObjectMapper().writeValueAsString(roleUserRequest);

        PermissionDto permissionDtoCreate = PermissionDto.builder()
                .permission("Create")
                .build();

        PermissionDto permissionDtoRead = PermissionDto.builder()
                .permission("Read")
                .build();

        List<PermissionDto> permissionDtos = List.of(
                permissionDtoCreate,
                permissionDtoRead
        );

        RoleDto roleUserDto = RoleDto.builder()
                .name(roleNameUserDto)
                .permissions(permissionDtos)
                .build();

        String roleUserDtoJson = new ObjectMapper().writeValueAsString(roleUserDto);

        Mockito.when(roleService.createNewRole(roleNameUserDto, rolePermissionsDto, adminAuthorization, appId))
                .thenReturn(roleUserDto);

        final String roleUserDtoResponse = mockMvc.perform(MockMvcRequestBuilders.post("/role")
                        .content(createRolUserRequest)
                        .header("Authorization", adminAuthorization)
                        .header("app_id", appId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(roleUserDtoResponse)
                .isEqualTo(roleUserDtoJson);

    }

}
