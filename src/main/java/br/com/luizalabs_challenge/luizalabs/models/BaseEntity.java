package br.com.luizalabs_challenge.luizalabs.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;

import java.io.Serializable;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

@MappedSuperclass
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(Types.VARCHAR)
    protected UUID uuid;

    @Version protected Integer version;

    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
    private boolean active;

    @PrePersist
    protected void prePersist() {
        active = true;
        updatedAt = createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}