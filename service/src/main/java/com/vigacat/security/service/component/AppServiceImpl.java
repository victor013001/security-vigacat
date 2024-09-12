package com.vigacat.security.service.component;

import com.vigacat.security.persistence.component.AppPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService{

    private final AppPersistence appPersistence;
    @Override
    public Long getAppIdByName(String appName) {
        return appPersistence.getAppIdByName(appName);
    }
}
