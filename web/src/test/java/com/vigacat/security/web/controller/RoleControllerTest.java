package com.vigacat.security.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigacat.security.persistence.dto.PermissionDto;
import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.service.component.RoleService;
import com.vigacat.security.web.dto.RoleRequest;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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

        String roleNameUser = "User";
        String permissionRead = "Read";
        String permissionCreate = "Create";

        Long appId = 1L;

        List<String> roleUserPermissions = List.of(permissionRead, permissionCreate);

        RoleRequest roleUserRequest = RoleRequest.builder()
                .permissions(roleUserPermissions)
                .name(roleNameUser)
                .build();

        String roleUserRequestJson = new ObjectMapper().writeValueAsString(roleUserRequest);

        PermissionDto permissionDtoCreate = PermissionDto.builder()
                .permission(permissionRead)
                .build();

        PermissionDto permissionDtoRead = PermissionDto.builder()
                .permission(permissionCreate)
                .build();

        List<PermissionDto> permissionDtos = List.of(
                permissionDtoCreate,
                permissionDtoRead
        );

        RoleDto roleUserDto = RoleDto.builder()
                .name(roleNameUser)
                .permissions(permissionDtos)
                .build();

        Mockito.when(roleService.createNewRole(roleNameUser, roleUserPermissions, appId))
                .thenReturn(roleUserDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/role")
                        .content(roleUserRequestJson)
                        .header("app_id", appId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(roleNameUser))
                .andExpect(MockMvcResultMatchers.jsonPath("$.permissions[0].permission").value(Matchers.is(permissionRead)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.permissions[1].permission").value(Matchers.is(permissionCreate)))
                .andDo(MockMvcResultHandlers.print());
    }

}
