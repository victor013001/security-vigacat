package com.vigacat.security.web.controller;

import com.vigacat.security.persistence.dto.RoleDto;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.service.component.UserService;
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
}
