CREATE TABLE public.genre
(
    id integer PRIMARY KEY NOT NULL,
    name varchar(100) NOT NULL
);
INSERT INTO public.genre (id, name) VALUES (1, 'Fantasy');
INSERT INTO public.genre (id, name) VALUES (2, 'Jeunesse');
INSERT INTO public.genre (id, name) VALUES (3, 'Fiction utopique et dystopique');
INSERT INTO public.genre (id, name) VALUES (4, 'Roman');
INSERT INTO public.genre (id, name) VALUES (5, 'Essai');