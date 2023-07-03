package com.vigacat.security.web.controller;

import com.vigacat.security.dao.entity.User;
import com.vigacat.security.dao.repository.UserRepository;
import com.vigacat.security.persistence.component.UserPersistenceImpl;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.service.component.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @InjectMocks
  private UserServiceImpl userService;

  @InjectMocks
  private UserPersistenceImpl userPersistence;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ModelMapper modelMapper;

  @Test
  public void getUser() throws
      Exception {

    String userName = "victor";
    Long appId = 1L;

    when(userRepository.findUserByUsernameAndAppId(userName,appId)).thenReturn(Optional.of(User.builder().id(1L).email(
        "victor@gmail.com").name("victor").roles(null).build()));

    when(userPersistence.getUserByUsernameAndApp(userName,
        appId)).thenReturn(userRepository.findUserByUsernameAndAppId(userName,appId).map(user -> modelMapper.map(user,
                                             UserDto.class))
                                      .orElseThrow());

    when(userService.getUser(userName,
        appId)).thenReturn(userPersistence.getUser("victor",1L));


    this.mockMvc.perform(get("/user").param("username", userName).param("app-id",
        String.valueOf(appId)
    )).andDo(print()).andExpect(status().isOk());
  }

}
