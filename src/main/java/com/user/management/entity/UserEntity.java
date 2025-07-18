package com.user.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "users")
public class UserEntity {

    @Id
    @UuidGenerator
    @Column(name = "user_id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "full_name", nullable = false, length = 50)
    private String name;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
