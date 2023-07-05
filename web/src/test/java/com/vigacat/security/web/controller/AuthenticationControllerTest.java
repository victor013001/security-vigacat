package com.vigacat.security.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigacat.security.service.component.security.TokenService;
import com.vigacat.security.service.component.security.VigacatAuthenticationManager;
import com.vigacat.security.web.dto.AuthenticationRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private VigacatAuthenticationManager authenticationManager;
    @Mock
    private TokenService tokenService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    public void authenticate() throws Exception {

        String username = "user";
        String userPassword = "dummy";
        String userPasswordEncode = "$2a$10$.uBLRQjhcB5ci.0AZyVpMeJblzjzINI362z6WZLov0pkcC/gVtc12";
        String userToken = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";

        List<GrantedAuthority> userAuthorities = List.of(
                new SimpleGrantedAuthority("LOGIN")
        );

        Authentication userAuthenticationRequest = new UsernamePasswordAuthenticationToken(username, userPassword);
        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(username, userPasswordEncode, userAuthorities);

        String request = new ObjectMapper().writeValueAsString(AuthenticationRequest.builder()
                .username(username)
                .password(userPassword)
                .build());

        Mockito.when(authenticationManager.authenticate(userAuthenticationRequest))
                .thenReturn(userAuthentication);

        Mockito.when(tokenService.createToken(username))
                .thenReturn(userToken);

        final String tokenResponse = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(tokenResponse)
                .isEqualTo(userToken);
    }

    @Test
    public void getPasswordEncoded() throws Exception {

        String userPassword = "dummy";
        String userPasswordEncode = "$2a$10$.uBLRQjhcB5ci.0AZyVpMeJblzjzINI362z6WZLov0pkcC/gVtc12";

        Mockito.when(passwordEncoder.encode(userPassword))
                .thenReturn(userPasswordEncode);

        final String passwordEncode = mockMvc.perform(MockMvcRequestBuilders.get("/auth")
                        .param("value", userPassword)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(passwordEncode)
                .isEqualTo(userPasswordEncode);
    }

}
