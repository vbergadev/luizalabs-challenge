package br.com.luizalabs_challenge.luizalabs.models;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@AttributeOverrides(
        value = {
                @AttributeOverride(name = "uuid", column = @Column(name = "uuid")),
                @AttributeOverride(name = "version", column = @Column(name = "version")),
                @AttributeOverride(name = "createdAt", column = @Column(name = "created_at")),
                @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at")),
                @AttributeOverride(name = "active", column = @Column(name = "active")),
        })
@Getter
@Setter
@ToString
@Generated
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCommunication extends BaseEntity implements Serializable {
    private LocalDateTime scheduledTime;
    private String recipient;
    private String message;
    private CommunicationStatus status;
    private SendMethod type;
}
