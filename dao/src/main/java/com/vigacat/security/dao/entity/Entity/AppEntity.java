package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Apps")
@Data
public class AppEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long appId;
  private String appName;

}
