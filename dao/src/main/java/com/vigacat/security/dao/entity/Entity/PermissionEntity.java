package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "Permissions")
@Data
public class PermissionEntity {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long permissionId;
  private String permission;
  @CreatedDate
  private LocalDate createdAt;
  private LocalDate updatedAt;
  private Long createdBy;
  private Long updatedBy;
}
