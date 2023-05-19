package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "RolePermissions")
@Data
public class RolePermissionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rolePermissionId;
  private Long roleId;
  private Long permissionId;
  @CreatedDate
  private LocalDate createdAt;
  private LocalDate updatedAt;
  private Long createdBy;
  private Long updatedBy;
}
