package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "UserRoles")
@Data
public class UserRoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userRoleId;
  private Long userId;
  private Long roleId;
  @CreatedDate
  private LocalDate createdAt;
  private LocalDate updatedAt;
  private Long createdBy;
  private Long updatedBy;
}
