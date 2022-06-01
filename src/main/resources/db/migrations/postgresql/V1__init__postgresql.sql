-- Pre-conditions:
-- The database (can be any name but use 'db_jawad' for naming consistency) has already been created within the server.
-- A user named 'user_jawad_flyway' has already been created and is set as the owner of the above database.

-- Note that the following create role script is for reference only. It is assumed that this user already exists.
-- (as mentioned above). Also note that this user should have privileges to create role and database so it can
-- create the role, user, and schema.

-- CREATE ROLE "user_jawad_flyway" WITH
-- 	   LOGIN
-- 	   NOSUPERUSER
-- 	   CREATEDB
-- 	   CREATEROLE
-- 	   INHERIT
-- 	   NOREPLICATION
-- 	   CONNECTION LIMIT -1
-- 	   PASSWORD 'user_jawad_flyway';

-- COMMENT ON ROLE "user_jawad_flyway" IS 'It is a  good practice to create a role that has the CREATEDB and CREATEROLE privileges, but is not a superuser, and then use this role for all routine management of databases and roles. This approach avoids the dangers of operating as a superuser.';

------------------------------
--- Start of migration script:
------------------------------

-- 1. Open connection to db_jawad database as user user_jawad_flyway.

-- 2. Create schema "schema_jawad":
-- CREATE SCHEMA schema_jawad;

-- 3. Create role thru which the main application user would get access to the schema.
--    One of the reason we are creating this role is because it is a best practice to grant privileges to users
--    via roles and not directly.

CREATE ROLE role_jawad WITH
    LOGIN
    NOSUPERUSER
    INHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION;

-- 4. Grant privileges on the newly created schema to the role:
--    Note that usage means the role is allowed to access the schema and default privs are for anything created in
--    in future in the schema.

GRANT USAGE ON SCHEMA schema_jawad TO role_jawad;

ALTER DEFAULT PRIVILEGES IN SCHEMA schema_jawad
GRANT INSERT, SELECT, UPDATE, DELETE ON TABLES TO role_jawad;

ALTER DEFAULT PRIVILEGES IN SCHEMA schema_jawad
GRANT ALL ON SEQUENCES TO role_jawad;

ALTER DEFAULT PRIVILEGES IN SCHEMA schema_jawad
GRANT EXECUTE ON FUNCTIONS TO role_jawad;

ALTER DEFAULT PRIVILEGES IN SCHEMA schema_jawad
GRANT USAGE ON TYPES TO role_jawad;


-- 5. Create the main user:

CREATE ROLE user_jawad WITH
    LOGIN
    NOSUPERUSER
    NOCREATEDB
    NOCREATEROLE
    INHERIT
    NOREPLICATION
    CONNECTION LIMIT -1
    PASSWORD 'user_jawad';

GRANT role_jawad TO user_jawad;



-- Note that we cannot assign a role as the owner of a sequence. It has to be a user. This is because when a sequence
-- is created, its owner is initally set to the creating user (and the owner needs to be changed later as an alter
-- statement). However if we alter the ownership of the table to be a role then postgre will not let us create its
-- sequence since the owner of the sequence at time of creating it will be the logged in user (which cannot be set
-- at creation time as mentioned above) but the owner of the table would be a role and due to this mismatch creation
-- of sequence will error out. So I think its better that only the db and the schema are owned by a role and ownership
--  of everything inside the schema is owned by a user rather than a role.

-- Table: schema_jawad.authorities

-- DROP TABLE IF EXISTS schema_jawad.authorities;

CREATE TABLE schema_jawad.authorities
(
    "authority_id" integer NOT NULL,
    "authority_name" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "record_version_id" integer NOT NULL,
    CONSTRAINT pk_authorities PRIMARY KEY ("authority_id"),
    CONSTRAINT "uk_authorities_authority_name" UNIQUE ("authority_name")
    )

    TABLESPACE pg_default;

CREATE SEQUENCE schema_jawad.seq_authorities
    OWNED BY schema_jawad.authorities."authority_id";

-- Table: schema_jawad.groups

-- DROP TABLE IF EXISTS schema_jawad.groups;

CREATE TABLE schema_jawad.groups
(
    "group_id" integer NOT NULL,
    "group_name" integer NOT NULL,
    "parent_group_id" integer,
    "record_version_id" integer NOT NULL,
    CONSTRAINT pk_groups PRIMARY KEY ("group_id"),
    CONSTRAINT "uk_groups_group_name" UNIQUE ("group_name"),
    CONSTRAINT "fk_groups_parent_group_id" FOREIGN KEY ("parent_group_id")
    REFERENCES schema_jawad.groups ("group_id") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

CREATE SEQUENCE schema_jawad.seq_groups
    OWNED BY schema_jawad.groups."group_id";

-- Table: schema_jawad.users

-- DROP TABLE IF EXISTS schema_jawad.users;

CREATE TABLE schema_jawad.users
(
    "user_id" integer NOT NULL,
    "user_name" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "is_enabled" boolean NOT NULL DEFAULT true,
    "primary_email_address" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "record_version_id" integer NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY ("user_id"),
    CONSTRAINT uk_users_user_name UNIQUE ("user_name"),
    CONSTRAINT uk_users_email UNIQUE ("primary_email_address")
    )

    TABLESPACE pg_default;

CREATE SEQUENCE schema_jawad.seq_users
    OWNED BY schema_jawad.users."user_id";


-- Table: schema_jawad.group_authorities

-- DROP TABLE IF EXISTS schema_jawad.group_authorities;

CREATE TABLE schema_jawad.group_authorities
(
    "group_id" integer NOT NULL,
    "authority_id" integer NOT NULL,
    "delegator_user_id" integer,
    "can_delegate" boolean,
    CONSTRAINT "pk_group_authorities" PRIMARY KEY ("group_id", "authority_id"),
    CONSTRAINT "fk_group_authorities_authority_id" FOREIGN KEY ("authority_id")
    REFERENCES schema_jawad.authorities ("authority_id") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_group_authorities_delegator_user_id" FOREIGN KEY ("delegator_user_id")
    REFERENCES schema_jawad.users ("user_id") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_group_authorities_group_id" FOREIGN KEY ("group_id")
    REFERENCES schema_jawad.groups ("group_id") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;


-- Table: schema_jawad.user_authorities

-- DROP TABLE IF EXISTS schema_jawad.user_authorities;

CREATE TABLE IF NOT EXISTS schema_jawad.user_authorities
(
    "user_id" integer NOT NULL,
    "authority_id" integer NOT NULL,
    "delegator_user_id" integer NOT NULL,
    "can_delegate" boolean NOT NULL,
    CONSTRAINT "pk_user_authotities" PRIMARY KEY ("user_id", "authority_id"),
    CONSTRAINT "fk_user_authorities_authority_id" FOREIGN KEY ("authority_id")
    REFERENCES schema_jawad.authorities ("authority_id") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_user_authorities_delegator_user_id" FOREIGN KEY ("delegator_user_id")
    REFERENCES schema_jawad.users ("user_id") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_user_authorities_user_id" FOREIGN KEY ("user_id")
    REFERENCES schema_jawad.users ("user_id") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;


-- Table: schema_jawad.user_groups

-- DROP TABLE IF EXISTS schema_jawad.user_groups;

CREATE TABLE IF NOT EXISTS schema_jawad.user_groups
(
    "user_id" integer NOT NULL,
    "group_id" integer NOT NULL,
    CONSTRAINT "pk_user_groups" PRIMARY KEY ("user_id", "group_id"),
    CONSTRAINT "fk_user_groups_group_id" FOREIGN KEY ("group_id")
    REFERENCES schema_jawad.groups ("group_id") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT "fk_user_groups_user_id" FOREIGN KEY ("user_id")
    REFERENCES schema_jawad.users ("user_id") MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;
