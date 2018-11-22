CREATE TABLE public.borrowed_book
(
    user_id integer NOT NULL,
    book_id integer NOT NULL,
    return_date date NOT NULL,
    extended boolean NOT NULL,
    returned boolean,
    borrowing_date timestamp NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_account (id),
    CONSTRAINT book_id_fk FOREIGN KEY (book_id) REFERENCES public.book (id)
);
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (1, 5, '2014-04-09', true, true, '2014-08-19 08:56:15.416000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (1, 2, '2019-01-17', false, true, '2018-12-27 08:58:57.692000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (1, 1, '2018-07-06', true, true, '2018-06-15 09:00:23.720000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (1, 6, '2014-06-06', false, false, '2014-05-16 09:00:54.646000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (14, 2, '2018-12-14', false, false, '2018-11-23 09:01:27.077000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (2, 3, '2018-06-23', false, false, '2018-06-02 08:55:52.255000');