-- liquibase formatted sql

-- changeset system:1
CREATE SCHEMA IF NOT EXISTS app_schema;

-- changeset system:2
CREATE TABLE IF NOT EXISTS app_schema.databasechangelog (
    id VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    filename VARCHAR(255) NOT NULL,
    dateexecuted TIMESTAMP NOT NULL,
    orderexecuted INT NOT NULL,
    exectype VARCHAR(10) NOT NULL,
    md5sum VARCHAR(35),
    description VARCHAR(255),
    comments VARCHAR(255),
    tag VARCHAR(255),
    liquibase VARCHAR(20),
    contexts VARCHAR(255),
    labels VARCHAR(255),
    deployment_id VARCHAR(10)
);

-- changeset system:3
-- includeAll path="gradle/liquibase/changelog/changes/" relativeToChangelogFile="true"
