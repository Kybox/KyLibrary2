CREATE TABLE public.user_level
(
    id integer PRIMARY KEY NOT NULL,
    label varchar(10)
);
INSERT INTO public.user_level (id, label) VALUES (1, 'admin');
INSERT INTO public.user_level (id, label) VALUES (2, 'manager');
INSERT INTO public.user_level (id, label) VALUES (3, 'user');