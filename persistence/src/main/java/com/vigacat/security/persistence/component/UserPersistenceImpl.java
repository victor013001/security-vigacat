package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.repository.UserRepository;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceImpl implements UserPersistence {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByUsernameAndApp(String username, Long appId) {
        return userRepository.findUserByUsernameAndAppId(username, appId)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(String username, Long appId) {
        return userRepository.findByNameAndRolesAppId(username, appId)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsernamePasswordDto> getUserByUsername(String username) {
        return userRepository.findByName(username)
                .map(user -> modelMapper.map(user, UsernamePasswordDto.class));
    }


}
