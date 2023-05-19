package com.vigacat.web.config;

import com.vigacat.security.dao.entity.Entity.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VigacatApplication {

  public static void main(String... args) {
    SpringApplication.run(VigacatApplication.class, args);
    UserEntity user = UserEntity.builder().userName("victor").password("1234").build();

    System.out.println("User: " + user.toString());
  }
}
