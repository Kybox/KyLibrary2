CREATE TABLE public.publisher
(
    id integer PRIMARY KEY NOT NULL,
    name varchar(100) NOT NULL
);
INSERT INTO public.publisher (id, name) VALUES (1, 'Pocket');
INSERT INTO public.publisher (id, name) VALUES (2, 'Gallimard');
INSERT INTO public.publisher (id, name) VALUES (3, 'Jouvence');