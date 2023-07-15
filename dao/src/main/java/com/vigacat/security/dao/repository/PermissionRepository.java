package com.vigacat.security.dao.repository;

import com.vigacat.security.dao.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "SELECT DISTINCT p " +
            "FROM Role r " +
            "JOIN r.permissions p " +
            "WHERE r.id IN :roleIds")
    List<Permission> findPermissionsByRoleId(@Param("roleIds") List<Long> roleIds);

    @Query(value = "SELECT DISTINCT p " +
            "FROM Permission p " +
            "WHERE p.permission IN :permissions")
    List<Permission> getPermissionsByNames(@Param("permissions") List<String> permissions);

}
