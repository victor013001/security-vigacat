package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Roles")
@Data
public class RoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "roleID")
  private Long roleId;

  private String roleName;

  @OneToMany
  @Column(name = "userRoleID")
  private List<UserRoleEntity> userRoleId;

  @OneToMany
  @Column(name = "rolePermissionID")
  private List<RolePermissionEntity> rolePermissionId;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Long createdBy;

  private Long updatedBy;

  @ManyToOne
  @Column(name = "appID")
  private AppEntity appId;
}

