package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Apps")
@Data
public class AppEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "appID")
  private Long appId;

  private String appName;

  @OneToMany
  @Column(name = "roleID")
  private List<RoleEntity> roleId;
}
