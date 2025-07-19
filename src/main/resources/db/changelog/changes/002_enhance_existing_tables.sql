-- liquibase formatted sql

-- changeset author:system:005
ALTER TABLE app_schema.groups 
ADD COLUMN IF NOT EXISTS description TEXT,
ADD COLUMN IF NOT EXISTS is_public BOOLEAN DEFAULT TRUE,
ADD COLUMN IF NOT EXISTS updated_at DATE;

-- changeset author:system:006
ALTER TABLE app_schema.messages 
ADD COLUMN IF NOT EXISTS created_at DATE,
ADD COLUMN IF NOT EXISTS updated_at DATE;

-- changeset author:system:007
ALTER TABLE app_schema.posts 
ADD COLUMN IF NOT EXISTS created_at DATE,
ADD COLUMN IF NOT EXISTS updated_at DATE;

-- changeset author:system:008
CREATE INDEX idx_messages_group_created ON app_schema.messages(group_id, created_at);
CREATE INDEX idx_messages_user_created ON app_schema.messages(user_id, created_at);
CREATE INDEX idx_posts_group_created ON app_schema.posts(group_id, created_at);
CREATE INDEX idx_posts_user_created ON app_schema.posts(user_id, created_at);
CREATE INDEX idx_groups_public ON app_schema.groups(is_public);
CREATE INDEX idx_groups_creator ON app_schema.groups(creator_id); 