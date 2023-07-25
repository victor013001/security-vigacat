package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.PermissionPersistence;
import com.vigacat.security.persistence.dto.PermissionDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PermissionServiceImplTest {

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Mock
    private PermissionPersistence permissionPersistence;

    @Test
    public void getPermissionsByName() {

        List<String> permissionNames = List.of(
                "Create",
                "Read"
        );

        PermissionDto permissionDtoCreate = PermissionDto.builder()
                .permission("Create")
                .build();

        PermissionDto permissionDtoRead = PermissionDto.builder()
                .permission("Read")
                .build();

        List<PermissionDto> permissionDtos = List.of(
                permissionDtoCreate,
                permissionDtoRead
        );

        Mockito.when(permissionPersistence.getPermissionsByNames(permissionNames))
                .thenReturn(permissionDtos);

        final List<PermissionDto> permissionDtoResponse = permissionService.getPermissionsByNames(permissionNames);

        Mockito.verify(permissionPersistence)
                .getPermissionsByNames(permissionNames);

        Assertions.assertThat(permissionDtoResponse)
                .extracting(PermissionDto::getPermission)
                .contains(
                        "Create",
                        "Read"
                );
    }
}
