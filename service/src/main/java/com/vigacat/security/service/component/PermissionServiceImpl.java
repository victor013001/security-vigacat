package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.PermissionPersistence;
import com.vigacat.security.persistence.dto.PermissionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private static final String LOG_PREFIX = "Permission Service >>";

    private final PermissionPersistence permissionPersistence;

    @Override
    public List<PermissionDto> getPermissionsByNames(List<String> permissionNames) {
        log.info("{} Get permissions by list of names", LOG_PREFIX);
        return permissionPersistence.getPermissionsByNames(permissionNames);
    }

}
