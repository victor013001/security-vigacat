package com.vigacat.security.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigacat.security.persistence.dto.*;
import com.vigacat.security.service.component.UserService;
import com.vigacat.security.web.constant.HeaderConstant;
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
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getUser() throws Exception {

        String userNameVictor = "victor";

        UserDto userDtoVictor = UserDto.builder()
                .name(userNameVictor)
                .email("victor@gmail.com")
                .roles(List.of(RoleDto.builder().name("Admin").permissions(null).build()))
                .build();

        Mockito.when(userService.getUser(
                        userNameVictor,
                        1L))
                .thenReturn(userDtoVictor);

        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                        .param("username", userNameVictor)
                        .param("app-id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(Matchers.is(userNameVictor)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[0].name").value(Matchers.is("Admin")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getUserSimple() throws Exception {

        String userNameVictor = "victor";

        UserDto userDtoVictor = UserDto.builder()
                .name(userNameVictor)
                .email("victor@gmail.com")
                .build();

        Mockito.when(userService.getUserWithoutFetch(
                        userNameVictor,
                        1L))
                .thenReturn(userDtoVictor);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/no-relations")
                        .param("username", userNameVictor)
                        .param("app-id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(Matchers.is(userNameVictor)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createNewUser() throws Exception {

        String username = "user";
        String userEmail = "user@email.com";
        String userPassword = "password";

        List<Long> userRoleIds = List.of(1L, 2L);
        List<RoleDto> userRolesDtos = List.of(
                RoleDto.builder()
                        .id(1L)
                        .build(),
                RoleDto.builder()
                        .id(2L)
                        .build()
        );

        UserToSaveDto userToSaveDto = UserToSaveDto.builder()
                .name(username)
                .email(userEmail)
                .password(userPassword)
                .roleIds(userRoleIds)
                .build();

        UserDto userDto = UserDto.builder()
                .name(username)
                .email(userEmail)
                .roles(userRolesDtos)
                .build();

        String userToSaveJson = new ObjectMapper().writeValueAsString(userToSaveDto);

        Mockito.when(userService.createNewUser(userToSaveDto))
                .thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .content(userToSaveJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(Matchers.is(username)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(Matchers.is(userEmail)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[1].id").value(2L))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getUserRolesAndPermissions() throws Exception {

        String username = "User";
        String userToken = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";
        String appName = "Catalogue";
        String permissionRead = "Read";

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

        Mockito.when(userService.getUserRolesAndPermissionsByTokenAndAppName(userToken, appName))
                        .thenReturn(userRolesPermissionsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/filter")
                        .header(HeaderConstant.AUTHORIZATION_NAME, userToken)
                        .header(HeaderConstant.APPLICATION_NAME, appName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(Matchers.is(username)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.permissions[0].permission").value(Matchers.is(permissionRead)))
                .andDo(MockMvcResultHandlers.print());
    }

}
