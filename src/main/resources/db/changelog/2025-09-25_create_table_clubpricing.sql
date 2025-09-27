CREATE TABLE club_pricing(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    club_id BIGINT NOT NULL,
    computer_status VARCHAR(20) NOT NULL,
    price_per_hour MONEY,
    CONSTRAINT fk_club_pricing_club FOREIGN KEY (club_id) REFERENCES computer_club(id) ON DELETE CASCADE
);