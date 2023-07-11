package com.vigacat.security.dao.repository;

import com.vigacat.security.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNameAndAppId(String roleName, Long appId);

    List<Role> findRolesByIdIn(List<Long> roleIds);
}
