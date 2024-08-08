-- liquibase formatted sql

-- changeset manxix69:1
CREATE TABLE z_notification_task (
     id              BIGSERIAL
    ,chat_id         TEXT        NOT NULL
    ,response_text   TEXT        NOT NULL
    ,date_create     TIMESTAMP
    ,date_start_send TIMESTAMP
    ,is_send         BOOL        NOT NULL DEFAULT FALSE
    ,date_send       TIMESTAMP
)