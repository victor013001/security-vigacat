package com.vigacat.security.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "app")
@Data
@Builder
public class App {

    @Column
    @Id
    private Long id;

    @Column
    private String name;

}
