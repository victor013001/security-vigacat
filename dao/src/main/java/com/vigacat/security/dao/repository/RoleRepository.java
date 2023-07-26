package com.vigacat.security.dao.repository;

import com.vigacat.security.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsRoleByNameAndAppId(String roleName, Long appId);

    @Query(value = "SELECT COUNT(r) = :roleIdsSize FROM Role r WHERE r.id IN (:roleIds)")
    boolean existsByIdIn(@Param("roleIds") List<Long> roleIds, @Param("roleIdsSize") int roleIdsSize);
}
