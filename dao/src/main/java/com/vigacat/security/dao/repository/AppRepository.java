package com.vigacat.security.dao.repository;

import com.vigacat.security.dao.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRepository extends JpaRepository<App, Long> {
    App getReferenceById(Long appId);

    Optional<App> getAppByName(String name);
}
