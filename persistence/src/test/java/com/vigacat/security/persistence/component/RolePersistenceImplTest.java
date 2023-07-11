package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.Role;
import com.vigacat.security.dao.repository.RoleRepository;
import com.vigacat.security.persistence.dto.RoleDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RolePersistenceImplTest {

    @InjectMocks
    private RolePersistenceImpl rolePersistence;

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ModelMapper modelMapper;


    @Test
    public void getRoleByNameAndAppId() {

        String roleAdminName = "Admin";
        Long appId = 1L;

        Role adminRole = Role.builder()
                .name(roleAdminName)
                .build();

        RoleDto adminRoleDto = RoleDto.builder()
                .name(roleAdminName)
                .build();

        Mockito.when(roleRepository.findByNameAndAppId(roleAdminName, appId))
                .thenReturn(Optional.of(adminRole));

        Mockito.when(modelMapper.map(Mockito.any(Role.class), Mockito.eq(RoleDto.class)))
                .thenReturn(adminRoleDto);

        final Optional<RoleDto> adminRoleDtoResponse = rolePersistence.getRoleByNameAndAppId(roleAdminName, appId);

        Mockito.verify(roleRepository)
                .findByNameAndAppId(roleAdminName, appId);

        Mockito.verify(modelMapper)
                .map(Mockito.any(Role.class), Mockito.eq(RoleDto.class));

        Assertions.assertThat(adminRoleDtoResponse)
                .contains(adminRoleDto);
    }

    @Test
    public void saveNewRole() {

        String roleAdminName = "Admin";
        String usernameAdmin = "userAdmin";

        Role adminRole = Role.builder()
                .name(roleAdminName)
                .build();

        RoleDto adminRoleDto = RoleDto.builder()
                .name(roleAdminName)
                .build();

        Mockito.when(modelMapper.map(Mockito.any(RoleDto.class), Mockito.eq(Role.class)))
                .thenReturn(adminRole);

        Mockito.when(roleRepository.save(adminRole))
                .thenReturn(adminRole);

        Mockito.when(modelMapper.map(Mockito.any(Role.class), Mockito.eq(RoleDto.class)))
                .thenReturn(adminRoleDto);

        final RoleDto adminRoleDtoResponse = rolePersistence.saveNewRole(adminRoleDto, usernameAdmin, 1L);

        Mockito.verify(modelMapper)
                .map(Mockito.any(RoleDto.class), Mockito.eq(Role.class));

        Mockito.verify(modelMapper)
                .map(Mockito.any(Role.class), Mockito.eq(RoleDto.class));

        Assertions.assertThat(adminRoleDtoResponse)
                .hasFieldOrPropertyWithValue("name", roleAdminName);
    }

    @Test
    public void getRolesById() {

        List<Long> roleIds = List.of(1L, 2L);

        List<Role> roles = List.of(
                Role.builder()
                        .id(1L)
                        .build(),
                Role.builder()
                        .id(2L)
                        .build()
        );

        List<RoleDto> roleDtos = List.of(
                RoleDto.builder()
                        .id(1L)
                        .build(),
                RoleDto.builder()
                        .id(2L)
                        .build()
        );


        Mockito.when(roleRepository.findRolesByIdIn(roleIds))
                .thenReturn(roles);

        Mockito.when(modelMapper.map(Mockito.any(Role.class), Mockito.eq(RoleDto.class)))
                .thenReturn(roleDtos.get(0), roleDtos.get(1));

        final List<RoleDto> roleDtosResponse = rolePersistence.getRolesById(roleIds);

        Mockito.verify(roleRepository)
                .findRolesByIdIn(roleIds);

        Mockito.verify(modelMapper, Mockito.times(2))
                .map(Mockito.any(Role.class), Mockito.eq(RoleDto.class));

        Assertions.assertThat(roleDtosResponse)
                .extracting(RoleDto::getId)
                .contains(1L, 2L);
    }

}
