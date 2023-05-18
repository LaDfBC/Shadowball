CREATE TABLE game (
    game_id             uuid NOT NULL PRIMARY KEY,
    session_number      integer,
    season_number       integer,
    currently_active    boolean NOT NULL
);

CREATE TABLE play (
    play_id             uuid NOT NULL PRIMARY KEY,
    pitch_value         integer,
    creation_date       date NOT NULL,
    game_id             uuid NOT NULL,
    CONSTRAINT play_game_id_fk FOREIGN KEY(game_id) REFERENCES game(game_id)
);

CREATE TABLE guess (
    member_id           text NOT NULL PRIMARY KEY,
    play_id             uuid NOT NULL,
    guessed_number      integer NOT NULL,
    difference          integer,
    member_name         text NOT NULL,
    CONSTRAINT guess_play_id_fk FOREIGN KEY(play_id) REFERENCES play(play_id)
);

