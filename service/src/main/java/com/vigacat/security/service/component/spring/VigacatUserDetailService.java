package com.vigacat.security.service.component.spring;

import com.vigacat.security.service.component.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VigacatUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByUsername(username)
                .map(userDto -> User.builder()
                        .username(userDto.getName())
                        .password(userDto.getPassword())
                        .authorities("LOGIN")
                        .build())
                .orElse(null);
    }
}
