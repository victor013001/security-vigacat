package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.User;
import com.vigacat.security.dao.repository.UserRepository;
import com.vigacat.security.persistence.dto.UserDto;
import com.vigacat.security.persistence.dto.UserToSaveDto;
import com.vigacat.security.persistence.dto.UsernamePasswordDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsersByNameOrEmail(String username, String email) {
        return userRepository.findUsersByNameOrEmail(username, email).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto saveNewUser(UserToSaveDto userDto, String usernameToken) {

        User userToSave = modelMapper.map(userDto, User.class);
        userToSave.setCreatedBy(usernameToken);
        userToSave.setCreatedAt(LocalDateTime.now());

        return modelMapper.map(userRepository.save(userToSave), UserDto.class);
    }


}
