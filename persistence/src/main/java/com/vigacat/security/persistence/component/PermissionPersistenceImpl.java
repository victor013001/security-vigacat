package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.repository.PermissionRepository;
import com.vigacat.security.persistence.dto.PermissionDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionPersistenceImpl implements PermissionPersistence {

    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getPermissionsByRoleIds(List<Long> roleIds) {
        return permissionRepository.findPermissionsByRoleId(roleIds).stream()
                .map(permission -> modelMapper.map(permission, PermissionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getPermissionsByNames(List<String> permissionNames) {
        return permissionRepository.getPermissionsByName(permissionNames).stream()
                .map(permission -> modelMapper.map(permission, PermissionDto.class))
                .collect(Collectors.toList());
    }

}
