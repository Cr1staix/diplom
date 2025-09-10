CREATE TABLE reservation(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    computer_id UUID NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_reservation_computer FOREIGN KEY (computer_id) REFERENCES computer(id),
    CONSTRAINT chk_reservation_time CHECK (start_time < end_time)
);