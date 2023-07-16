package com.vigacat.security.dao.repository;

import com.vigacat.security.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u" +
            " FROM User u" +
            " JOIN FETCH u.roles r" +
            " JOIN r.app a" +
            " WHERE a.id = :appId" +
            " AND u.name = :username")
    Optional<User> findUserByUsernameAndAppId(@Param("username") String username, @Param("appId") Long appId);

    Optional<User> findByNameAndRolesAppId(String name, Long id);

    Optional<User> findByName(String name);

    boolean existsUsersByNameOrEmail(String name, String email);
}
