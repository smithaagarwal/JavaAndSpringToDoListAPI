DROP TABLE IF EXISTS task;
CREATE TABLE task (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    description varchar(255) NOT NULL,
    is_complete BOOLEAN NOT NULL,
    version integer NOT NULL
);