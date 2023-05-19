package com.vigacat.security.dao.entity.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "Users")
@Data
@Builder
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;
  private String userName;
  private String password;
  @CreatedDate
  private LocalDate createdAt;
  private LocalDate updatedAt;
  private Long createdBy;
  private Long updatedBy;
}
