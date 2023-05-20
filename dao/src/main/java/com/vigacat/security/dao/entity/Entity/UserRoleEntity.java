package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "UserRoles")
@Data
public class UserRoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userRoleID")
  private Long userRoleId;

  @ManyToOne
  @Column(name = "userID")
  private UserEntity userId;

  @ManyToOne
  @Column(name = "roleID")
  private RoleEntity roleId;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Long createdBy;

  private Long updatedBy;
}
