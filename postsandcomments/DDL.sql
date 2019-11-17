-- SEQUENCE: public.comments_comment_id_seq

-- DROP SEQUENCE public.comments_comment_id_seq;

CREATE SEQUENCE public.comments_comment_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.comments_comment_id_seq
    OWNER TO postgres;
	
-- SEQUENCE: public.posts_post_id_seq

-- DROP SEQUENCE public.posts_post_id_seq;

CREATE SEQUENCE public.posts_post_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.posts_post_id_seq
    OWNER TO postgres;
-- Table: public.posts

-- DROP TABLE public.posts;

CREATE TABLE public.posts
(
    post_id bigint NOT NULL DEFAULT nextval('posts_post_id_seq'::regclass),
    location_city text COLLATE pg_catalog."default" NOT NULL,
    created_dt timestamp without time zone NOT NULL,
    post_text text COLLATE pg_catalog."default" NOT NULL,
    user_name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT posts_pkey PRIMARY KEY (post_id)
)

TABLESPACE pg_default;

ALTER TABLE public.posts
    OWNER to postgres;

-- Index: created_dt_post_index

-- DROP INDEX public.created_dt_post_index;

CREATE INDEX created_dt_post_index
    ON public.posts USING btree
    (created_dt)
    TABLESPACE pg_default;

-- Index: post_user_name_index

-- DROP INDEX public.post_user_name_index;

CREATE INDEX post_user_name_index
    ON public.posts USING btree
    (user_name COLLATE pg_catalog."default")
    TABLESPACE pg_default;
-- Table: public.comments

-- DROP TABLE public.comments;

CREATE TABLE public.comments
(
    comment_id bigint NOT NULL DEFAULT nextval('comments_comment_id_seq'::regclass),
    comment_text text COLLATE pg_catalog."default" NOT NULL,
    created_dt timestamp without time zone NOT NULL,
    post_id bigint NOT NULL,
    CONSTRAINT comments_pkey PRIMARY KEY (comment_id),
    CONSTRAINT fk_post_id FOREIGN KEY (post_id)
        REFERENCES public.posts (post_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.comments
    OWNER to postgres;

-- Index: comment_created_date_index

-- DROP INDEX public.comment_created_date_index;

CREATE INDEX comment_created_date_index
    ON public.comments USING btree
    (created_dt)
    TABLESPACE pg_default;

-- Index: fk_post_id_index

-- DROP INDEX public.fk_post_id_index;

CREATE INDEX fk_post_id_index
    ON public.comments USING btree
    (post_id)
    TABLESPACE pg_default;