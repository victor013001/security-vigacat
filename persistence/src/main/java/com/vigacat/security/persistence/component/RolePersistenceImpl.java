package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.Role;
import com.vigacat.security.dao.repository.AppRepository;
import com.vigacat.security.dao.repository.RoleRepository;
import com.vigacat.security.persistence.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class RolePersistenceImpl implements RolePersistence {

    private final RoleRepository roleRepository;
    private final AppRepository appRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public RoleDto saveNewRole(RoleDto roleDto, Long appId, String usernameAuthenticated) {
        Role roleToSave = createRole(roleDto, appId, usernameAuthenticated);
        return modelMapper.map(roleRepository.save(roleToSave), RoleDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean roleNameExist(String roleName, Long appId) {
        return roleRepository.existsRoleByNameAndAppId(roleName, appId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean roleIdsExist(List<Long> roleIds) {
        return roleRepository.existsByIdIn(roleIds, roleIds.size());
    }

    private Role createRole(RoleDto roleDto, Long appId, String usernameAuthenticated) {
        Role role = modelMapper.map(roleDto, Role.class);
        role.setApp(appRepository.getReferenceById(appId));
        role.setCreatedBy(usernameAuthenticated);
        role.setCreatedAt(LocalDateTime.now());
        return role;
    }

}
