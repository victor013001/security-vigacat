package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "Roles")
@Data
public class RoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roleId;
  @Enumerated(EnumType.STRING)
  private EnumRoleName roleName;
  @CreatedDate
  private LocalDate createdAt;
  private LocalDate updatedAt;
  private Long createdBy;
  private Long updatedBy;
  private Long appId;
}

