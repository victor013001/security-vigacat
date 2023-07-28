package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.Role;
import com.vigacat.security.dao.entity.User;
import com.vigacat.security.dao.repository.RoleRepository;
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
    private final RoleRepository roleRepository;
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
    public boolean userNameOrEmailExist(String username, String email) {
        return userRepository.existsUsersByNameOrEmail(username, email);
    }

    @Override
    @Transactional
    public UserDto saveNewUser(UserToSaveDto userToSaveDto, String usernameAuthenticated) {
        User userToSave = createUserToSave(userToSaveDto, usernameAuthenticated);
        return modelMapper.map(userRepository.save(userToSave), UserDto.class);
    }

    private User createUserToSave(UserToSaveDto userToSaveDto, String usernameAuthenticated) {
        User userToSave = modelMapper.map(userToSaveDto, User.class);
        userToSave.setRoles(getRoleReferences(userToSaveDto.getRoleIds()));
        userToSave.setCreatedBy(usernameAuthenticated);
        userToSave.setCreatedAt(LocalDateTime.now());
        return userToSave;
    }

    private List<Role> getRoleReferences(List<Long> roleIds) {
        return roleIds.stream()
                .map(roleRepository::getReferenceById)
                .collect(Collectors.toList());
    }

}
