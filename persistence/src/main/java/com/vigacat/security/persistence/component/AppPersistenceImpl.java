package com.vigacat.security.persistence.component;

import com.vigacat.security.dao.entity.App;
import com.vigacat.security.dao.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AppPersistenceImpl implements AppPersistence {

    private final AppRepository appRepository;

    @Override
    @Transactional
    public Long getAppIdByName(String appName) {
        return appRepository.getAppByName(appName)
                .map(App::getId)
                .orElseThrow();
    }
}
