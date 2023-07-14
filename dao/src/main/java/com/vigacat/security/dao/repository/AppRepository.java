package com.vigacat.security.dao.repository;

import com.vigacat.security.dao.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App, Long> {
    App getReferenceById(Long appId);
}
