CREATE TABLE IF NOT EXISTS public.counterparty
(
    id uuid NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT counterparty_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.counterparty
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.files
(
    id uuid NOT NULL,
    date bigint,
    is_counted boolean,
    is_downloaded boolean,
    name character varying(255) COLLATE pg_catalog."default",
    url character varying(255) COLLATE pg_catalog."default",
    counterparty_id uuid,
    CONSTRAINT files_pkey PRIMARY KEY (id),
    CONSTRAINT fk1sxxbbkqypf8m63o40i7wj2jf FOREIGN KEY (counterparty_id)
        REFERENCES public.counterparty (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.files
    OWNER to postgres;