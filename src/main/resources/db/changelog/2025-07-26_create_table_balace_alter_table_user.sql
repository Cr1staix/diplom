CREATE TABLE balance(
                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
                        user_id BIGINT NOT NULL,
                        computer_club_id BIGINT NOT NULL ,
                        money MONEY NOT NULL CHECK ( money >= 0 ),
                        CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id),
                        CONSTRAINT fk_computer_club FOREIGN KEY(computer_club_id) REFERENCES computer_club(id),
                        CONSTRAINT unique_user_club_balance UNIQUE(user_id, computer_club_id)
);

ALTER TABLE users
ADD COLUMN balance_id BIGINT NOT NULL;

ALTER TABLE users
ADD CONSTRAINT balance_user FOREIGN KEY (balance_id) REFERENCES balance(id);

