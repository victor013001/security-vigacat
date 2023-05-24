package com.vigacat.security.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App {

    @Column
    @Id
    private Long id;

    @Column
    private String name;

}
