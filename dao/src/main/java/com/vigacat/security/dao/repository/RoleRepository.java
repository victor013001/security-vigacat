package com.vigacat.security.dao.repository;

import com.vigacat.security.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsRoleByNameAndAppId(String roleName, Long appId);

}
