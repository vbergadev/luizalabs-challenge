package br.com.luizalabs_challenge.luizalabs.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseEntityTest {

    private BaseEntity baseEntity;
    private UUID uuid;
    private Integer version;
    private ZonedDateTime now;

    @BeforeEach
    public void setUp() {
        uuid = UUID.randomUUID();
        version = 1;
        now = ZonedDateTime.now();
        baseEntity = new BaseEntity(uuid, version, now, now, true);
    }

    @Test
    public void testNoArgsConstructor() {
        BaseEntity entity = new BaseEntity();
        assertThat(entity).isNotNull();
        assertThat(entity.getUuid()).isNull();
        assertThat(entity.getVersion()).isNull();
        assertThat(entity.getCreatedAt()).isNull();
        assertThat(entity.getUpdatedAt()).isNull();
        assertThat(entity.isActive()).isFalse();
    }

    @Test
    public void testAllArgsConstructor() {
        assertThat(baseEntity.getUuid()).isEqualTo(uuid);
        assertThat(baseEntity.getVersion()).isEqualTo(version);
        assertThat(baseEntity.getCreatedAt()).isEqualTo(now);
        assertThat(baseEntity.getUpdatedAt()).isEqualTo(now);
        assertThat(baseEntity.isActive()).isTrue();
    }

    @Test
    public void testGettersAndSetters() {
        BaseEntity entity = new BaseEntity();
        entity.setUuid(uuid);
        entity.setVersion(version);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setActive(true);

        assertThat(entity.getUuid()).isEqualTo(uuid);
        assertThat(entity.getVersion()).isEqualTo(version);
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getUpdatedAt()).isEqualTo(now);
        assertThat(entity.isActive()).isTrue();
    }

    @Test
    public void testPrePersist() {
        BaseEntity entity = new BaseEntity();
        entity.prePersist();

        assertThat(entity.isActive()).isTrue();
        assertThat(entity.getCreatedAt()).isNotNull();
        assertThat(entity.getUpdatedAt()).isNotNull();
        assertThat(entity.getCreatedAt()).isEqualTo(entity.getUpdatedAt());
    }

    @Test
    public void testPreUpdate() {
        baseEntity.prePersist();
        ZonedDateTime createdAt = baseEntity.getCreatedAt();

        baseEntity.preUpdate();

        assertThat(baseEntity.getUpdatedAt()).isNotNull();
        assertThat(baseEntity.getUpdatedAt()).isNotEqualTo(createdAt);
    }

    @Test
    public void testEqualsAndHashCode() {
        BaseEntity entity1 = new BaseEntity(uuid, version, now, now, true);
        BaseEntity entity2 = new BaseEntity(uuid, version, now, now, true);

        assertThat(entity1).isEqualTo(entity2);
        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
    }
}