--liquibase formatted sql
--changeset Cr1staix:CUM-3

CREATE TABLE user
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    phone      VARCHAR(12),
    date_of_birth DATE,
    added_at   TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE computer_club
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(128),
    address    TEXT,
    computer_id UUID,
    added_at   TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT cmp_cl_cmp FOREIGN KEY (computer_id) REFERENCES computer(id)
);

CREATE TABLE specifications(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    monitor TEXT,
    keyboard TEXT,
    mouse TEXT,
    headphones TEXT,
    cpu TEXT,
    gpu TEXT,
    ram INT
);

CREATE TABLE computer(
    id UUID GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name INT,
    spec_id BIGINT,
    status VARCHAR(50),
    CONSTRAINT computer_spec_id FOREIGN KEY (spec_id) REFERENCES specifications(id),
    added_at   TIMESTAMP,
    updated_at TIMESTAMP
);

