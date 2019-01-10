CREATE TABLE public.author
(
    id integer PRIMARY KEY NOT NULL,
    name varchar(100) NOT NULL
);
INSERT INTO public.author (id, name) VALUES (1, 'J.R.R. TOLKIEN');
INSERT INTO public.author (id, name) VALUES (2, 'Antoine de Saint-Exupéry');
INSERT INTO public.author (id, name) VALUES (3, 'George Orwell');
INSERT INTO public.author (id, name) VALUES (4, 'André Gide');
INSERT INTO public.author (id, name) VALUES (5, 'Aldous HUXLEY');
INSERT INTO public.author (id, name) VALUES (6, 'Miguel Ruiz');
CREATE TABLE public.book
(
    id integer PRIMARY KEY NOT NULL,
    isbn varchar(20) NOT NULL,
    title varchar(100) NOT NULL,
    author_id integer NOT NULL,
    publisher_id integer NOT NULL,
    publishdate date,
    summary text,
    genre_id integer NOT NULL,
    available integer,
    cover varchar(200),
    nb_copies integer NOT NULL,
    bookable boolean NOT NULL,
    return_date date,
    available_for_booking integer DEFAULT 0,
    reservations integer DEFAULT 0,
    CONSTRAINT author_id_fk FOREIGN KEY (author_id) REFERENCES public.author (id),
    CONSTRAINT publisher_id_fk FOREIGN KEY (publisher_id) REFERENCES public.publisher (id),
    CONSTRAINT genre_id_fk FOREIGN KEY (genre_id) REFERENCES public.genre (id)
);
CREATE UNIQUE INDEX uk_2rqqpgnve6gu3ar7prin5qm0i ON public.book (isbn);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies, bookable, return_date, available_for_booking, reservations) VALUES (4, '978-2070368792', 'Les faux-monnayeurs', 4, 2, '1972-06-29', 'Depuis quelque temps, des pièces de fausse monnaie circulent. J''en suis averti. Je n''ai pas encore réussi à découvrir leur provenance. Mais je sais que le jeune Georges - tout naïvement je veux le croire - est un de ceux qui s''en servent et les mettent en circulation. Ils sont quelques-uns, de l''âge de votre neveu, qui se prêtent à ce honteux trafic. Je ne mets pas en doute qu''on abuse de leur innocence et que ces enfants sans discernement ne jouent le rôle de dupes entre les mains de quelques coupables aînés.', 4, 1, 'https://images-na.ssl-images-amazon.com/images/I/81MCnnWKvNL.jpg', 1, false, null, 0, 0);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies, bookable, return_date, available_for_booking, reservations) VALUES (6, '978-2889116546', 'Les quatre accords toltèques', 6, 3, '2016-01-08', 'Découvrez ou redécouvrez Les quatre accords toltèques, et prenez comme des millions de lecteurs en France et à travers le monde, la voie de la liberté personnelle.
Dans ce livre, Don Miguel révèle la source des croyances limitatrices qui nous privent de joie et créent des souffrances inutiles. Il montre en des termes très simples comment on peut se libérer du conditionnement collectif - le "rêve de la planète", basé sur la peur - afin de retrouver la dimension d''amour inconditionnel qui est à notre origine et constitue le fondement des enseignements toltèques que Castenada fut le premier à faire découvrir au grand public. Don Miguel révèle ici 4 clés simples pour transformer sa vie et ses relations, tirées de la sagesse toltèque. Leur application au quotidien permet de transformer rapidement notre vie en une expérience de liberté, de vrai bonheur et d''amour.', 5, 1, 'https://images-na.ssl-images-amazon.com/images/I/81jfJUEh2AL.jpg', 1, false, null, 0, 0);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies, bookable, return_date, available_for_booking, reservations) VALUES (1, '978-2266232999', 'Le Seigneur des Anneaux', 1, 1, '2012-11-22', 'La Terre est peuplée d''innombrables créatures étranges. Les Hobbits, apparentés à l''homme, mais proches également des Elfes et des Nains, vivent en paix au nord-ouest de l''Ancien Monde, dans la Comté. Paix précaire et menacée, cependant, depuis que Bilbon Sacquet a dérobé au monstre Gollum l''anneau de Puissance jadis forgé par Sauron de Mordor. Car cet anneau est doté d''un pouvoir immense et maléfique. Il permet à son détenteur de se rendre invisible et lui confère une autorité sans limite sur les possesseurs des autres anneaux. Bref, il fait de lui le Maître du Monde. C''est pourquoi Sauron s''est juré de reconquérir l''anneau par tous les moyens. Déjà ses Cavaliers Noirs rôdent aux frontières de la Comté…. Ainsi débute la trilogie du Seigneur des anneaux.', 1, 1, 'https://images-na.ssl-images-amazon.com/images/I/41ngIhV8tKL.jpg', 1, false, null, 0, 0);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies, bookable, return_date, available_for_booking, reservations) VALUES (2, '978-2070612758', 'Le petit prince', 2, 2, '2007-03-15', 'Le premier soir, je me suis donc endormi sur le sable à mille milles de toute terre habitée. J''étais bien plus isolé qu''un naufragé sur un radeau au milieu de l''océan. Alors, vous imaginez ma surprise, au lever du jour, quand une drôle de petite voix m''a réveillé. Elle disait : “S''il vous plaît... dessine-moi un mouton !” J''ai bien regardé. Et j''ai vu ce petit bonhomme tout à fait extraordinaire qui me considérait gravement...', 2, 1, 'https://images-na.ssl-images-amazon.com/images/I/71lWtUcX47L.jpg', 1, false, null, 0, 0);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies, bookable, return_date, available_for_booking, reservations) VALUES (3, '978-2072730030', '1984', 3, 2, '2018-05-24', 'Année 1984 en Océanie. 1984 ? C''est en tout cas ce qu''il semble à Winston, qui ne saurait toutefois en jurer. Le passé a été oblitéré et réinventé, et les événements les plus récents sont susceptibles d''être modifiés. Winston est lui-même chargé de récrire les archives qui contredisent le présent et les promesses de Big Brother. Grâce à une technologie de pointe, ce dernier sait tout, voit tout. Il n''est pas une âme dont il ne puisse connaître les pensées. On ne peut se fier à personne et les enfants sont encore les meilleurs espions qui soient. Liberté est Servitude. Ignorance est Puissance. Telles sont les devises du régime de Big Brother. La plupart des Océaniens n''y voient guère à redire, surtout les plus jeunes qui n''ont pas connu l''époque de leurs grands-parents et le sens initial du mot «libre». Winston refuse cependant de perdre espoir. Il entame une liaison secrète et hautement dangereuse avec l''insoumise Julia et tous deux vont tenter d''intégrer la Fraternité, une organisation ayant pour but de renverser Big Brother. Mais celui-ci veille... Le célèbre et glaçant roman de George Orwell se redécouvre dans une nouvelle traduction, plus directe et plus dépouillée, qui tente de restituer la terreur dans toute son immédiateté mais aussi les tonalités nostalgiques et les échappées lyriques d''une œuvre brutale et subtile, équivoque et génialement manipulatrice.', 3, 1, 'https://images-na.ssl-images-amazon.com/images/I/51rr0JcuqjL.jpg', 1, false, null, 0, 0);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies, bookable, return_date, available_for_booking, reservations) VALUES (5, '978-2266283038', 'Le meilleur des mondes', 5, 1, '2017-08-17', 'Voici près d''un siècle, dans d''étourdissantes visions, Aldous Huxley imagine une civilisation future jusque dans ses rouages les plus surprenants : un État Mondial, parfaitement hiérarchisé, a cantonné les derniers humains " sauvages " dans des réserves. La culture in vitro des fœtus a engendré le règne des " Alphas ", génétiquement déterminés à être l''élite dirigeante. Les castes inférieures, elles, sont conditionnées pour se satisfaire pleinement de leur sort. Dans cette société où le bonheur est loi, famille, monogamie, sentiments sont bannis. Le meilleur des mondes est possible. Aujourd''hui, il nous paraît même familier...', 3, 0, 'https://images-na.ssl-images-amazon.com/images/I/511KW0qCjCL.jpg', 1, true, '2019-02-06', 1, -1);
CREATE TABLE public.borrowed_book
(
    user_id integer NOT NULL,
    book_id integer NOT NULL,
    return_date date NOT NULL,
    extended boolean NOT NULL,
    returned boolean,
    borrowing_date timestamp,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_account (id),
    CONSTRAINT book_id_fk FOREIGN KEY (book_id) REFERENCES public.book (id)
);
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (1, 5, '2014-04-09', true, true, '2014-08-19 08:56:15.416000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (1, 1, '2018-07-06', true, true, '2018-06-15 09:00:23.720000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (14, 2, '2019-01-03', true, true, '2018-11-23 09:01:27.077000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (1, 6, '2014-06-06', false, true, '2014-05-16 09:00:54.646000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (15, 1, '2019-01-05', false, true, '2019-01-03 22:00:38.451000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (2, 3, '2019-01-10', true, false, '2018-06-02 08:55:52.255000');
INSERT INTO public.borrowed_book (user_id, book_id, return_date, extended, returned, borrowing_date) VALUES (1, 2, '2018-12-20', false, false, '2018-12-27 08:58:57.692000');
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
CREATE TABLE public.publisher
(
    id integer PRIMARY KEY NOT NULL,
    name varchar(100) NOT NULL
);
INSERT INTO public.publisher (id, name) VALUES (1, 'Pocket');
INSERT INTO public.publisher (id, name) VALUES (2, 'Gallimard');
INSERT INTO public.publisher (id, name) VALUES (3, 'Jouvence');
CREATE TABLE public.reserved_book
(
    user_id integer NOT NULL,
    book_id integer NOT NULL,
    reserve_date timestamp NOT NULL,
    pending boolean DEFAULT true NOT NULL,
    notified boolean DEFAULT false NOT NULL,
    notification_date timestamp,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_account (id),
    CONSTRAINT book_id_fk FOREIGN KEY (book_id) REFERENCES public.book (id)
);
INSERT INTO public.reserved_book (user_id, book_id, reserve_date, pending, notified, notification_date) VALUES (1, 5, '2019-01-09 11:53:51.818000', true, true, '2019-01-09 13:01:19.237000');
INSERT INTO public.reserved_book (user_id, book_id, reserve_date, pending, notified, notification_date) VALUES (2, 5, '2019-01-09 12:22:45.204000', true, false, null);
CREATE TABLE public.token_storage
(
    token varchar(36) PRIMARY KEY NOT NULL,
    creation timestamp DEFAULT now() NOT NULL,
    expire timestamp NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_account (id)
);
INSERT INTO public.token_storage (token, creation, expire, user_id) VALUES ('2fce88e5-3147-4607-831f-b41bd2194f64', '2019-01-05 22:49:52.058000', '2019-01-05 22:59:52.058000', 15);
INSERT INTO public.token_storage (token, creation, expire, user_id) VALUES ('439824de-92b9-4232-86b1-aace30cc14d6', '2019-01-09 16:53:08.080000', '2019-01-09 17:03:08.080000', 1);
CREATE TABLE public.user_account
(
    id integer PRIMARY KEY NOT NULL,
    password varchar(60),
    first_name varchar(100),
    last_name varchar(100),
    birthday date,
    postal_address varchar(200),
    tel varchar(20),
    level_id integer DEFAULT 1 NOT NULL,
    email varchar(255),
    alert_sender boolean DEFAULT true NOT NULL,
    CONSTRAINT user_level_fk FOREIGN KEY (level_id) REFERENCES public.user_level (id)
);
INSERT INTO public.user_account (id, password, first_name, last_name, birthday, postal_address, tel, level_id, email, alert_sender) VALUES (4, '$2a$10$ynKx16tLp7N28l3zymlW0O349BBx3gS0478KCKaxxPeOQi.m3GByq', 'Manager', 'Yan', '1983-05-19', '18 rue de Bel Air 47000 Agen', '0619785454', 2, 'manager@kylibrary.fr', true);
INSERT INTO public.user_account (id, password, first_name, last_name, birthday, postal_address, tel, level_id, email, alert_sender) VALUES (2, '$2y$10$oWX4QwiWhMsSsJhOwj0cJ.LHkUz8sXhcBxsQ4/TmsBZRQlEJR1arS', 'Yan', 'Ky', '1980-06-20', '6 rue Laborde', '0678542569', 3, 'nslr@riseup.net', true);
INSERT INTO public.user_account (id, password, first_name, last_name, birthday, postal_address, tel, level_id, email, alert_sender) VALUES (3, '$2a$10$nW7UOJuTiqP6v7.oeWZk7eR4OSHpVfmtDeFhwnXmnE3i32ZKwCBFa', 'Admin', 'Yan', '1983-05-19', '18 rue de Bel Air 47000 Agen', '0619785454', 1, 'admin@kylibrary.fr', true);
INSERT INTO public.user_account (id, password, first_name, last_name, birthday, postal_address, tel, level_id, email, alert_sender) VALUES (16, '$2y$10$0b1WVqssA46uOS4v3.sCAuVXCTV9iaKoIxwGwEhKV.K/xaRbjIodi', 'Marc', 'Delsol', '1981-07-18', '54 rue Tondu 33000 Bordeaux', '0614582735', 3, 'marc@delsol.fr', true);
INSERT INTO public.user_account (id, password, first_name, last_name, birthday, postal_address, tel, level_id, email, alert_sender) VALUES (17, '$2y$10$pxBjOqpYNKuS.ZGh.Gljnuu1DhDv.X4SQkCZo/juDkHTIfOVNiIy.', 'Annie', 'Colin', '1990-09-18', '107 rue Deveaux 33200 Bordeaux', '0345653218', 3, 'annie@colin.fr', true);
INSERT INTO public.user_account (id, password, first_name, last_name, birthday, postal_address, tel, level_id, email, alert_sender) VALUES (14, '$2y$10$1hea17Rf5bxXTYWOac58vuyaZCcL5D7nzc970Pwq40jOt.dKs7HIG', 'Luc', 'Delaval', '1986-05-15', '10 rue de Belle Ville 75011 Paris', '0126578248', 3, 'luc@delaval.fr', true);
INSERT INTO public.user_account (id, password, first_name, last_name, birthday, postal_address, tel, level_id, email, alert_sender) VALUES (1, '$2a$12$UMLERoIeZukh.tcnpeOUreZpZ5hBrll0YECkf9dYLqmCOYZVO6ohq', 'Lou', 'Lou', '2008-05-15', '10 rue de la paix 75011 Paris', '0600120012', 3, 'lou@lou.fr', true);
INSERT INTO public.user_account (id, password, first_name, last_name, birthday, postal_address, tel, level_id, email, alert_sender) VALUES (15, '$2a$04$pLzEC6.9vinm3tO1W2gIB.E6K.VUdSYDcpcSLG9tqA/AslcZboyya', 'Alain', 'Perce', '1984-01-19', '12 rue de Villeneuve 35000 Rennes', '0685439686', 3, 'alain@perce.fr', true);
CREATE TABLE public.user_level
(
    id integer PRIMARY KEY NOT NULL,
    label varchar(10)
);
INSERT INTO public.user_level (id, label) VALUES (1, 'admin');
INSERT INTO public.user_level (id, label) VALUES (2, 'manager');
INSERT INTO public.user_level (id, label) VALUES (3, 'user');