package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.Permission;
import com.vigacat.security.dao.repository.PermissionRepository;
import com.vigacat.security.persistence.dto.PermissionDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PermissionPersistenceImplTest {

    @InjectMocks
    private PermissionPersistenceImpl permissionPersistence;

    @Mock
    private PermissionRepository permissionRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void getPermissionsByRoleIds() {

        List<Long> roleIds = List.of(1L, 2L);

        Permission readPermission = Permission.builder().permission("Read").build();
        PermissionDto readPermissionDto = PermissionDto.builder().permission("Read").build();

        Permission createPermission = Permission.builder().permission("Create").build();
        PermissionDto createPermissionDto = PermissionDto.builder().permission("Create").build();

        List<Permission> permissionList = List.of(
                readPermission,
                createPermission
        );
        List<PermissionDto> permissionDtoList = List.of(
                readPermissionDto,
                createPermissionDto
        );

        Mockito.when(permissionRepository.findPermissionsByRoleId(roleIds))
                .thenReturn(permissionList);

        Mockito.when(modelMapper.map(Mockito.any(Permission.class), Mockito.eq(PermissionDto.class)))
                .thenReturn(permissionDtoList.get(0), permissionDtoList.get(1));

        final List<PermissionDto> permissionDtoResponseList = permissionPersistence.getPermissionsByRoleIds(roleIds);

        Mockito.verify(permissionRepository)
                .findPermissionsByRoleId(roleIds);

        Mockito.verify(modelMapper, Mockito.times(2))
                .map(Mockito.any(Permission.class), Mockito.eq(PermissionDto.class));

        Assertions.assertThat(permissionDtoResponseList)
                .extracting(PermissionDto::getPermission)
                .contains(
                        "Read",
                        "Create"
                );
    }
    
    @Test
    public void getPermissionsByNames() {

        List<String> permissionNames = List.of(
                "Read",
                "Create"
        );

        Permission readPermission = Permission.builder().permission("Read").build();
        PermissionDto readPermissionDto = PermissionDto.builder().permission("Read").build();

        Permission createPermission = Permission.builder().permission("Create").build();
        PermissionDto createPermissionDto = PermissionDto.builder().permission("Create").build();

        List<Permission> permissionList = List.of(
                readPermission,
                createPermission
        );

        List<PermissionDto> permissionDtoList = List.of(
                readPermissionDto,
                createPermissionDto
        );

        Mockito.when(permissionRepository.getPermissionsByName(permissionNames))
                .thenReturn(permissionList);

        Mockito.when(modelMapper.map(Mockito.any(Permission.class), Mockito.eq(PermissionDto.class)))
                .thenReturn(permissionDtoList.get(0), permissionDtoList.get(1));

        final List<PermissionDto> permissionsDtoResponse = permissionPersistence.getPermissionsByNames(permissionNames);

        Mockito.verify(permissionRepository)
                .getPermissionsByName(permissionNames);

        Mockito.verify(modelMapper, Mockito.times(2))
                .map(Mockito.any(Permission.class), Mockito.eq(PermissionDto.class));

        Assertions.assertThat(permissionsDtoResponse)
                .extracting(PermissionDto::getPermission)
                .contains(
                        "Read",
                        "Create"
                );
    }
}
