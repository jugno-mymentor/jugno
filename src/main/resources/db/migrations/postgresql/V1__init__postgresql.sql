-- -- Database user for Flyway migrations.
-- CREATE ROLE "user_jawad_flyway" WITH
-- 	LOGIN
-- 	NOSUPERUSER
-- 	CREATEDB
-- 	CREATEROLE
-- 	INHERIT
-- 	NOREPLICATION
-- 	CONNECTION LIMIT -1
-- 	PASSWORD 'xxxxxx';
-- COMMENT ON ROLE "user_jawad_flyway" IS 'It is a  good practice to create a role that has the CREATEDB and CREATEROLE privileges, but is not a superuser, and then use this role for all routine management of databases and roles. This approach avoids the dangers of operating as a superuser.';
--
-- -- Database user for use by application:
-- CREATE ROLE user_jawad WITH
--     LOGIN
--     NOSUPERUSER
--     NOCREATEDB
--     NOCREATEROLE
--     NOINHERIT
--     NOREPLICATION
--     CONNECTION LIMIT -1
--     PASSWORD 'jawad';
--
-- CREATE SCHEMA schema_jawad
--     AUTHORIZATION user_jawad;

-- GRANT USAGE ON SCHEMA schema_jawad TO user_jawad;

-- Table: schema_jawad.authorities

-- DROP TABLE IF EXISTS schema_jawad.authorities;

CREATE TABLE IF NOT EXISTS schema_jawad.authorities
(
    "authorityId" integer NOT NULL,
    "authorityName" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "recordVersionId" integer NOT NULL,
    CONSTRAINT pk_authorities PRIMARY KEY ("authorityId"),
    CONSTRAINT "uk_authorities_authorityName" UNIQUE ("authorityName")
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS schema_jawad.authorities
    OWNER to user_jawad;


CREATE SEQUENCE schema_jawad.seq_authorities
    OWNED BY schema_jawad.authorities."authorityId";

ALTER SEQUENCE schema_jawad.seq_authorities
    OWNER TO user_jawad;


-- Table: schema_jawad.groups

-- DROP TABLE IF EXISTS schema_jawad.groups;

CREATE TABLE IF NOT EXISTS schema_jawad.groups
(
    "groupId" integer NOT NULL,
    "groupName" integer NOT NULL,
    "parentGroupId" integer,
    "recordVersionId" integer NOT NULL,
    CONSTRAINT pk_groups PRIMARY KEY ("groupId"),
    CONSTRAINT "uk_groups_groupName" UNIQUE ("groupName"),
    CONSTRAINT "fk_groups_parentGroupId" FOREIGN KEY ("parentGroupId")
    REFERENCES schema_jawad.groups ("groupId") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS schema_jawad.groups
    OWNER to user_jawad;


CREATE SEQUENCE schema_jawad.seq_groups
    OWNED BY schema_jawad.groups."groupId";

ALTER SEQUENCE schema_jawad.seq_groups
    OWNER TO user_jawad;


-- Table: schema_jawad.users

-- DROP TABLE IF EXISTS schema_jawad.users;

CREATE TABLE IF NOT EXISTS schema_jawad.users
(
    "userId" integer NOT NULL,
    "userName" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "isEnabled" boolean NOT NULL DEFAULT true,
    "primaryEmailAddress" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "recordVersionId" integer NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY ("userId"),
    CONSTRAINT uk_users_userName UNIQUE ("userName"),
    CONSTRAINT uk_users_email UNIQUE ("primaryEmailAddress")
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS schema_jawad.users
    OWNER to user_jawad;


CREATE SEQUENCE schema_jawad.seq_users
    OWNED BY schema_jawad.users."userId";

ALTER SEQUENCE schema_jawad.seq_users
    OWNER TO user_jawad;


-- Table: schema_jawad.group_authorities

-- DROP TABLE IF EXISTS schema_jawad.group_authorities;

CREATE TABLE IF NOT EXISTS schema_jawad.group_authorities
(
    "groupId" integer NOT NULL,
    "authorityId" integer NOT NULL,
    "delegatorUserId" integer,
    "canDelegate" boolean,
    CONSTRAINT "pk_group_authorities" PRIMARY KEY ("groupId", "authorityId"),
    CONSTRAINT "fk_group_authorities_authorityId" FOREIGN KEY ("authorityId")
    REFERENCES schema_jawad.authorities ("authorityId") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_group_authorities_delegatorUserId" FOREIGN KEY ("delegatorUserId")
    REFERENCES schema_jawad.users ("userId") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_group_authorities_groupId" FOREIGN KEY ("groupId")
    REFERENCES schema_jawad.groups ("groupId") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS schema_jawad.group_authorities
    OWNER to user_jawad;

-- Table: schema_jawad.user_authorities

-- DROP TABLE IF EXISTS schema_jawad.user_authorities;

CREATE TABLE IF NOT EXISTS schema_jawad.user_authorities
(
    "userId" integer NOT NULL,
    "authorityId" integer NOT NULL,
    "delegatorUserId" integer NOT NULL,
    "canDelegate" boolean NOT NULL,
    CONSTRAINT "pk_user_authotities" PRIMARY KEY ("userId", "authorityId"),
    CONSTRAINT "fk_user_authorities_authorityId" FOREIGN KEY ("authorityId")
    REFERENCES schema_jawad.authorities ("authorityId") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_user_authorities_delegatorUserId" FOREIGN KEY ("delegatorUserId")
    REFERENCES schema_jawad.users ("userId") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_user_authorities_userId" FOREIGN KEY ("userId")
    REFERENCES schema_jawad.users ("userId") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS schema_jawad.user_authorities
    OWNER to user_jawad;


-- Table: schema_jawad.user_groups

-- DROP TABLE IF EXISTS schema_jawad.user_groups;

CREATE TABLE IF NOT EXISTS schema_jawad.user_groups
(
    "userId" integer NOT NULL,
    "groupId" integer NOT NULL,
    CONSTRAINT "pk_user_groups" PRIMARY KEY ("userId", "groupId"),
    CONSTRAINT "fk_user_groups_groupId" FOREIGN KEY ("groupId")
    REFERENCES schema_jawad.groups ("groupId") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_user_groups_userId" FOREIGN KEY ("userId")
    REFERENCES schema_jawad.users ("userId") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS schema_jawad.user_groups
    OWNER to user_jawad;
