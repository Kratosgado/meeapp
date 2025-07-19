-- liquibase formatted sql

-- changeset author:system:001
CREATE TABLE IF NOT EXISTS app_schema.events (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    event_date TIMESTAMP NOT NULL,
    location VARCHAR(255),
    max_participants INTEGER,
    is_public BOOLEAN DEFAULT TRUE,
    creator_id VARCHAR(255) NOT NULL,
    group_id VARCHAR(255),
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL,
    CONSTRAINT fk_events_creator FOREIGN KEY (creator_id) REFERENCES app_schema.users(id) ON DELETE CASCADE,
    CONSTRAINT fk_events_group FOREIGN KEY (group_id) REFERENCES app_schema.groups(id) ON DELETE CASCADE
);

-- changeset author:system:002
CREATE TABLE IF NOT EXISTS app_schema.event_participants (
    event_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (event_id, user_id),
    CONSTRAINT fk_event_participants_event FOREIGN KEY (event_id) REFERENCES app_schema.events(id) ON DELETE CASCADE,
    CONSTRAINT fk_event_participants_user FOREIGN KEY (user_id) REFERENCES app_schema.users(id) ON DELETE CASCADE
);

-- changeset author:system:003
CREATE TABLE IF NOT EXISTS app_schema.event_interests (
    event_id VARCHAR(255) NOT NULL,
    interests VARCHAR(255) NOT NULL,
    CONSTRAINT fk_event_interests_event FOREIGN KEY (event_id) REFERENCES app_schema.events(id) ON DELETE CASCADE
);

-- changeset author:system:004
CREATE INDEX idx_events_creator ON app_schema.events(creator_id);
CREATE INDEX idx_events_group ON app_schema.events(group_id);
CREATE INDEX idx_events_date ON app_schema.events(event_date);
CREATE INDEX idx_events_public ON app_schema.events(is_public);
CREATE INDEX idx_event_participants_user ON app_schema.event_participants(user_id);
CREATE INDEX idx_event_participants_event ON app_schema.event_participants(event_id); 