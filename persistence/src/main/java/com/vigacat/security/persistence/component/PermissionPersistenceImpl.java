package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.repository.PermissionRepository;
import com.vigacat.security.persistence.dto.PermissionDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionPersistenceImpl implements PermissionPersistence {

    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PermissionDto> getPermissionsByRoleIds(List<Long> roleIds) {
        return permissionRepository.findPermissionsByRoleId(roleIds).stream()
                .map(permission -> modelMapper.map(permission, PermissionDto.class))
                .collect(Collectors.toList());
    }

}
