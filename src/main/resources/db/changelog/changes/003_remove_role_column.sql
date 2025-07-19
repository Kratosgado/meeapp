-- liquibase formatted sql

-- changeset author:system:009
ALTER TABLE app_schema.users DROP COLUMN IF EXISTS role; 