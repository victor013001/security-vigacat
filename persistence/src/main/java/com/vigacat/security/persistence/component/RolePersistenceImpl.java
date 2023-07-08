package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.App;
import com.vigacat.security.dao.entity.Role;
import com.vigacat.security.dao.repository.RoleRepository;
import com.vigacat.security.persistence.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RolePersistenceImpl implements RolePersistence {

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDto> getRoleByNameAndAppId(String roleName, Long appId) {
        return roleRepository.findByNameAndAppId(roleName,appId)
                .map(role -> modelMapper.map(role, RoleDto.class));
    }

    @Override
    @Transactional
    public RoleDto saveNewRole(RoleDto roleDto, String username, Long appId) {

        Role roleToSave = modelMapper.map(roleDto, Role.class);
        roleToSave.setApp(App.builder()
                .id(appId)
                .build());
        roleToSave.setCreatedBy(username);
        roleToSave.setCreatedAt(LocalDateTime.now());

        return modelMapper.map(roleRepository.save(roleToSave), RoleDto.class);
    }


}
