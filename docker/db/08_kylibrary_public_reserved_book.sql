CREATE TABLE public.reserved_book
(
  user_id integer NOT NULL,
  book_id integer NOT NULL,
  reserve_date TIMESTAMP NOT NULL,
  pending boolean,
  CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_account (id),
  CONSTRAINT book_id_fk FOREIGN KEY (book_id) REFERENCES public.book (id)
);