CREATE DATABASE test
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE SCHEMA public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT ALL ON SCHEMA public TO postgres;

GRANT ALL ON SCHEMA public TO PUBLIC;

CREATE TABLE sampleentity
(
    "Id" numeric NOT NULL,
    uuid text COLLATE "default".pg_catalog,
    name text COLLATE "default".pg_catalog,
    CONSTRAINT sampleentity_pkey PRIMARY KEY ("Id")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE sampleentity OWNER to postgres;