package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Permissions")
@Data
public class PermissionEntity {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column(name = "permissionID")
  private Long permissionId;

  private String permission;

  @OneToMany
  private List<RolePermissionEntity> rolePermissionId;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Long createdBy;

  private Long updatedBy;
}
