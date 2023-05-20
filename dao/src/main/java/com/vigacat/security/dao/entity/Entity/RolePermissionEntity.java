package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "RolePermissions")
@Data
public class RolePermissionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rolePermissionId")
  private Long rolePermissionId;

  @ManyToOne
  @Column(name = "roleID")
  private RoleEntity roleId;

  @ManyToOne
  @Column(name = "permissionID")
  private PermissionEntity permissionId;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Long createdBy;

  private Long updatedBy;
}
