CREATE TABLE public.borrowed_book
(
    user_id integer NOT NULL,
    book_id integer NOT NULL,
    return_date date NOT NULL,
    extended boolean NOT NULL,
    returned boolean,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_account (id),
    CONSTRAINT book_id_fk FOREIGN KEY (book_id) REFERENCES public.book (id)
);
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned) VALUES (2, 3, '2018-06-23', false, false);
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned) VALUES (1, 5, '2014-04-09', true, true);
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned) VALUES (1, 2, '2019-01-17', false, false);
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned) VALUES (1, 1, '2019-07-06', true, true);
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned) VALUES (1, 6, '2014-06-06', false, false);