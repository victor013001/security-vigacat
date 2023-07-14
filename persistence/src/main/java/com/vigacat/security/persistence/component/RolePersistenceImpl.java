package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.Role;
import com.vigacat.security.dao.repository.AppRepository;
import com.vigacat.security.dao.repository.RoleRepository;
import com.vigacat.security.persistence.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RolePersistenceImpl implements RolePersistence {

    private final RoleRepository roleRepository;
    private final AppRepository appRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDto> getRoleByNameAndAppId(String roleName, Long appId) {
        return roleRepository.findByNameAndAppId(roleName, appId)
                .map(role -> modelMapper.map(role, RoleDto.class));
    }

    @Override
    @Transactional
    public RoleDto saveNewRole(RoleDto roleDto, Long appId) {
        Role roleToSave = createRole(roleDto, appId);
        return modelMapper.map(roleRepository.save(roleToSave), RoleDto.class);
    }

    private Role createRole(RoleDto roleDto, Long appId) {

        String usernameAuthenticated = SecurityContextHolder.getContext().getAuthentication().getName();

        Role role = modelMapper.map(roleDto, Role.class);

        role.setApp(appRepository.getReferenceById(appId));

        role.setCreatedBy(usernameAuthenticated);
        role.setCreatedAt(LocalDateTime.now());

        return role;
    }

    @Override
    public boolean roleNameExist(String roleName, Long appId) {
        return roleRepository.existsRoleByNameAndAppId(roleName, appId);
    }


}
