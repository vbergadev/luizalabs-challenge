package br.com.luizalabs_challenge.luizalabs.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "schedule")
@Data
@MappedSuperclass
@Getter
@Setter
@ToString
@Generated
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleComunication {
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

    private ZonedDateTime scheduledTime;
    private String recipient;
    private String message;
    private String status = "PENDING";
    private String type;
}


