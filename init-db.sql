CREATE TABLE notice
(
    id        SERIAL PRIMARY KEY,
    message   TEXT NOT NULL,
    type      TEXT NOT NULL,
    processed BOOLEAN DEFAULT FALSE
);

