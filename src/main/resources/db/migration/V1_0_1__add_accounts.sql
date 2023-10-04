CREATE TABLE IF NOT EXISTS public.account
(
    id uuid NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    role smallint,
    user_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT account_role_check CHECK (role >= 0 AND role <= 1)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.account
    OWNER to postgres;
