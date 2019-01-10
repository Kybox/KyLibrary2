CREATE TABLE public.user_account
(
    id integer PRIMARY KEY NOT NULL,
    alertSender varchar(60) NOT NULL,
    password varchar(60),
    first_name varchar(100),
    last_name varchar(100),
    birthday date,
    postal_address varchar(200),
    tel varchar(20),
    level_id integer DEFAULT 1 NOT NULL,
    CONSTRAINT user_level_fk FOREIGN KEY (level_id) REFERENCES public.user_level (id)
);
INSERT INTO public.user_account (id, alertSender, password, first_name, last_name, birthday, postal_address, tel, level_id) VALUES (4, 'manager@kylibrary.fr', '$2a$10$ynKx16tLp7N28l3zymlW0O349BBx3gS0478KCKaxxPeOQi.m3GByq', 'Manager', 'Yan', '1983-05-19', '18 rue de Bel Air 47000 Agen', '0619785454', 2);
INSERT INTO public.user_account (id, alertSender, password, first_name, last_name, birthday, postal_address, tel, level_id) VALUES (14, 'luc@delaval.fr', null, 'Luc', 'Delaval', '1986-05-15', '10 rue de Belle Ville 75011 Paris', '0126578248', 3);
INSERT INTO public.user_account (id, alertSender, password, first_name, last_name, birthday, postal_address, tel, level_id) VALUES (1, 'lou@lou.fr', '$2a$12$UMLERoIeZukh.tcnpeOUreZpZ5hBrll0YECkf9dYLqmCOYZVO6ohq', 'Lou', 'Lou', '2008-05-15', '10 rue de la paix 75011 Paris', '0600120012', 3);
INSERT INTO public.user_account (id, alertSender, password, first_name, last_name, birthday, postal_address, tel, level_id) VALUES (3, 'admin@kylibrary.fr', '$2a$10$nW7UOJuTiqP6v7.oeWZk7eR4OSHpVfmtDeFhwnXmnE3i32ZKwCBFa', 'Admin', 'Yan', '1983-05-19', '18 rue de Bel Air 47000 Agen', '0619785454', 1);
INSERT INTO public.user_account (id, alertSender, password, first_name, last_name, birthday, postal_address, tel, level_id) VALUES (2, 'nslr@riseup.net', '$2y$10$oWX4QwiWhMsSsJhOwj0cJ.LHkUz8sXhcBxsQ4/TmsBZRQlEJR1arS', 'Yan', 'Ky', '1980-06-20', '6 rue Laborde', '0678542569', 3);