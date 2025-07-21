--liquibase formatted sql
--changeset Cr1staix:CUM-3

CREATE TABLE users
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name    VARCHAR(50),
    last_name     VARCHAR(50),
    phone         VARCHAR(12) NOT NULL UNIQUE ,
    date_of_birth DATE NOT NULL ,
    added_at      TIMESTAMP,
    updated_at    TIMESTAMP
);

CREATE TABLE computer_specification
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    monitor    TEXT NOT NULL ,
    keyboard   TEXT NOT NULL,
    mouse      TEXT NOT NULL,
    headphones TEXT NOT NULL,
    cpu        TEXT NOT NULL,
    gpu        TEXT NOT NULL,
    ram        TEXT NOT NULL
);

CREATE TABLE computer_club
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(128),
    address    TEXT,
    added_at   TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT uq_computer_club_name_address UNIQUE (name, address)
);

CREATE TABLE computer
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name             VARCHAR(50) NOT NULL ,
    spec_id          BIGINT,
    status           VARCHAR(50),
    computer_club_id BIGINT,
    CONSTRAINT computer_spec_id FOREIGN KEY (spec_id) REFERENCES computer_specification(id),
    CONSTRAINT cmp_id_cmp FOREIGN KEY (computer_club_id) REFERENCES computer_club (id),
    added_at         TIMESTAMP,
    updated_at       TIMESTAMP
);







