package com.vigacat.security.dao.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AuditedEntity {

    @Column
    private String createdBy;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String updatedBy;

    @Column
    private LocalDateTime updatedAt;

}
