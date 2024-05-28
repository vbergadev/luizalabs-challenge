CREATE TABLE schedule (
    uuid CHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version INT NOT NULL DEFAULT 1,
    scheduled_time TIMESTAMP NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    message VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT pk_communication PRIMARY KEY (uuid)
);