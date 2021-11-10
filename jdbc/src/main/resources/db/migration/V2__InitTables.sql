CREATE TABLE actor (
                       id BIGSERIAL PRIMARY KEY,
                       name TEXT NOT NULL,
                       movie BIGINT REFERENCES movie(id),
                       unique(name, movie)
);