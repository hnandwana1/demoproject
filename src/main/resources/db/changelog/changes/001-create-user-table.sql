--liquibase formatted sql

--changeset developer:001-create-users-table
CREATE TABLE IF NOT EXISTS users (
    user_id UUID PRIMARY KEY,
    full_name VARCHAR(50) NOT NULL,
    address VARCHAR(200) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--changeset developer:001-create-users-indexes
CREATE INDEX IF NOT EXISTS idx_users_full_name ON users(full_name);
CREATE INDEX IF NOT EXISTS idx_users_created_at ON users(created_at);

--rollback DROP INDEX IF EXISTS idx_users_created_at;
--rollback DROP INDEX IF EXISTS idx_users_full_name;
--rollback DROP TABLE IF EXISTS users;