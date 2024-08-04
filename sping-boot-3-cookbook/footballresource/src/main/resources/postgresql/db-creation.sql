CREATE DATABASE football
    WITH
    OWNER = user
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c football

CREATE TABLE IF NOT EXISTS teams
(
    id   integer GENERATED BY DEFAULT AS IDENTITY,
    name character varying(255),
    CONSTRAINT teams_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS players
(
    id            integer GENERATED BY DEFAULT AS IDENTITY,
    jersey_number integer,
    name          character varying(255),
    position      character varying(255),
    date_of_birth date,
    team_id       integer,
    CONSTRAINT players_pkey PRIMARY KEY (id),
    CONSTRAINT fk_players_teams FOREIGN KEY (team_id)
        REFERENCES teams (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

CREATE TABLE IF NOT EXISTS albums
(
    title       character varying(255),
    description character varying(255),
    expire_date date,
    CONSTRAINT albums_pkey PRIMARY KEY (title)
);
