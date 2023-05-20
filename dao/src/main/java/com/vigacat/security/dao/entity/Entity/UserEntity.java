package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "userID")
  private Long userId;

  private String userName;

  private String userEmail;

  private String password;

  @OneToMany
  private List<UserRoleEntity> userRoleId;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Long createdBy;

  private Long updatedBy;
}
