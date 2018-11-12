CREATE TABLE public.token_storage
(
    token varchar(36) PRIMARY KEY UNIQUE NOT NULL,
    creation TIMESTAMP NOT NULL DEFAULT now(),
    expire TIMESTAMP NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_account(id)
);