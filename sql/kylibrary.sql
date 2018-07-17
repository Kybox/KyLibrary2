--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.8
-- Dumped by pg_dump version 9.6.8

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS kylibrary;
--
-- Name: kylibrary; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE kylibrary WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'UTF8' LC_CTYPE = 'UTF8';


ALTER DATABASE kylibrary OWNER TO postgres;

\connect kylibrary

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.author (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.author OWNER TO postgres;

--
-- Name: author_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.author_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.author_id_seq OWNER TO postgres;

--
-- Name: author_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.author_id_seq OWNED BY public.author.id;


--
-- Name: book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book (
    id integer NOT NULL,
    isbn character varying(20) NOT NULL,
    title character varying(100) NOT NULL,
    author_id integer NOT NULL,
    publisher_id integer NOT NULL,
    publishdate date,
    summary text,
    genre_id integer NOT NULL,
    available integer,
    cover character varying(200)
);


ALTER TABLE public.book OWNER TO postgres;

--
-- Name: book_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.book_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.book_id_seq OWNER TO postgres;

--
-- Name: book_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.book_id_seq OWNED BY public.book.id;


--
-- Name: borrowed_book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.borrowed_book (
    user_id integer NOT NULL,
    book_id integer NOT NULL,
    return_date date NOT NULL,
    extended boolean NOT NULL,
    returned boolean
);


ALTER TABLE public.borrowed_book OWNER TO postgres;

--
-- Name: genre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.genre (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.genre OWNER TO postgres;

--
-- Name: genre_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.genre_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.genre_id_seq OWNER TO postgres;

--
-- Name: genre_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.genre_id_seq OWNED BY public.genre.id;


--
-- Name: publisher; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.publisher (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.publisher OWNER TO postgres;

--
-- Name: publisher_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.publisher_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.publisher_id_seq OWNER TO postgres;

--
-- Name: publisher_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.publisher_id_seq OWNED BY public.publisher.id;


--
-- Name: user_account; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_account (
    id integer NOT NULL,
    email character varying(60) NOT NULL,
    password character varying(60),
    first_name character varying(100),
    last_name character varying(100),
    birthday date,
    postal_address character varying(200),
    tel character varying(20),
    level_id integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.user_account OWNER TO postgres;

--
-- Name: user_account_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_account_id_seq OWNER TO postgres;

--
-- Name: user_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_account_id_seq OWNED BY public.user_account.id;


--
-- Name: user_level; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_level (
    id integer NOT NULL,
    label character varying(10)
);


ALTER TABLE public.user_level OWNER TO postgres;

--
-- Name: user_level_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_level_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_level_id_seq OWNER TO postgres;

--
-- Name: user_level_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_level_id_seq OWNED BY public.user_level.id;


--
-- Name: author id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author ALTER COLUMN id SET DEFAULT nextval('public.author_id_seq'::regclass);


--
-- Name: book id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book ALTER COLUMN id SET DEFAULT nextval('public.book_id_seq'::regclass);


--
-- Name: genre id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genre ALTER COLUMN id SET DEFAULT nextval('public.genre_id_seq'::regclass);


--
-- Name: publisher id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publisher ALTER COLUMN id SET DEFAULT nextval('public.publisher_id_seq'::regclass);


--
-- Name: user_account id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_account ALTER COLUMN id SET DEFAULT nextval('public.user_account_id_seq'::regclass);


--
-- Name: user_level id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_level ALTER COLUMN id SET DEFAULT nextval('public.user_level_id_seq'::regclass);


--
-- Data for Name: author; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.author (id, name) FROM stdin;
1	J.R.R. TOLKIEN
2	Antoine de Saint-Exupéry
3	George Orwell
4	André Gide
5	Aldous HUXLEY
6	Miguel Ruiz
\.


--
-- Name: author_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.author_id_seq', 6, true);


--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover) FROM stdin;
5	978-2266283038	Le meilleur des mondes	5	1	2017-08-17	Voici près d'un siècle, dans d'étourdissantes visions, Aldous Huxley imagine une civilisation future jusque dans ses rouages les plus surprenants : un État Mondial, parfaitement hiérarchisé, a cantonné les derniers humains " sauvages " dans des réserves. La culture in vitro des fœtus a engendré le règne des " Alphas ", génétiquement déterminés à être l'élite dirigeante. Les castes inférieures, elles, sont conditionnées pour se satisfaire pleinement de leur sort. Dans cette société où le bonheur est loi, famille, monogamie, sentiments sont bannis. Le meilleur des mondes est possible. Aujourd'hui, il nous paraît même familier...	3	1	https://images-na.ssl-images-amazon.com/images/I/511KW0qCjCL.jpg
3	978-2072730030	1984	3	2	2018-05-24	Année 1984 en Océanie. 1984 ? C'est en tout cas ce qu'il semble à Winston, qui ne saurait toutefois en jurer. Le passé a été oblitéré et réinventé, et les événements les plus récents sont susceptibles d'être modifiés. Winston est lui-même chargé de récrire les archives qui contredisent le présent et les promesses de Big Brother. Grâce à une technologie de pointe, ce dernier sait tout, voit tout. Il n'est pas une âme dont il ne puisse connaître les pensées. On ne peut se fier à personne et les enfants sont encore les meilleurs espions qui soient. Liberté est Servitude. Ignorance est Puissance. Telles sont les devises du régime de Big Brother. La plupart des Océaniens n'y voient guère à redire, surtout les plus jeunes qui n'ont pas connu l'époque de leurs grands-parents et le sens initial du mot «libre». Winston refuse cependant de perdre espoir. Il entame une liaison secrète et hautement dangereuse avec l'insoumise Julia et tous deux vont tenter d'intégrer la Fraternité, une organisation ayant pour but de renverser Big Brother. Mais celui-ci veille... Le célèbre et glaçant roman de George Orwell se redécouvre dans une nouvelle traduction, plus directe et plus dépouillée, qui tente de restituer la terreur dans toute son immédiateté mais aussi les tonalités nostalgiques et les échappées lyriques d'une œuvre brutale et subtile, équivoque et génialement manipulatrice.	3	1	https://images-na.ssl-images-amazon.com/images/I/51rr0JcuqjL.jpg
2	978-2070612758	Le petit prince	2	2	2007-03-15	Le premier soir, je me suis donc endormi sur le sable à mille milles de toute terre habitée. J'étais bien plus isolé qu'un naufragé sur un radeau au milieu de l'océan. Alors, vous imaginez ma surprise, au lever du jour, quand une drôle de petite voix m'a réveillé. Elle disait : “S'il vous plaît... dessine-moi un mouton !” J'ai bien regardé. Et j'ai vu ce petit bonhomme tout à fait extraordinaire qui me considérait gravement...	2	1	https://images-na.ssl-images-amazon.com/images/I/71lWtUcX47L.jpg
1	978-2266232999	Le Seigneur des Anneaux	1	1	2012-11-22	La Terre est peuplée d'innombrables créatures étranges. Les Hobbits, apparentés à l'homme, mais proches également des Elfes et des Nains, vivent en paix au nord-ouest de l'Ancien Monde, dans la Comté. Paix précaire et menacée, cependant, depuis que Bilbon Sacquet a dérobé au monstre Gollum l'anneau de Puissance jadis forgé par Sauron de Mordor. Car cet anneau est doté d'un pouvoir immense et maléfique. Il permet à son détenteur de se rendre invisible et lui confère une autorité sans limite sur les possesseurs des autres anneaux. Bref, il fait de lui le Maître du Monde. C'est pourquoi Sauron s'est juré de reconquérir l'anneau par tous les moyens. Déjà ses Cavaliers Noirs rôdent aux frontières de la Comté…. Ainsi débute la trilogie du Seigneur des anneaux.	1	1	https://images-na.ssl-images-amazon.com/images/I/41ngIhV8tKL.jpg
4	978-2070368792	Les faux-monnayeurs	4	2	1972-06-29	Depuis quelque temps, des pièces de fausse monnaie circulent. J'en suis averti. Je n'ai pas encore réussi à découvrir leur provenance. Mais je sais que le jeune Georges - tout naïvement je veux le croire - est un de ceux qui s'en servent et les mettent en circulation. Ils sont quelques-uns, de l'âge de votre neveu, qui se prêtent à ce honteux trafic. Je ne mets pas en doute qu'on abuse de leur innocence et que ces enfants sans discernement ne jouent le rôle de dupes entre les mains de quelques coupables aînés.	4	1	https://images-na.ssl-images-amazon.com/images/I/81MCnnWKvNL.jpg
6	978-2889116546	Les quatre accords toltèques	6	3	2016-01-08	Découvrez ou redécouvrez Les quatre accords toltèques, et prenez comme des millions de lecteurs en France et à travers le monde, la voie de la liberté personnelle. \r\nDans ce livre, Don Miguel révèle la source des croyances limitatrices qui nous privent de joie et créent des souffrances inutiles. Il montre en des termes très simples comment on peut se libérer du conditionnement collectif - le "rêve de la planète", basé sur la peur - afin de retrouver la dimension d'amour inconditionnel qui est à notre origine et constitue le fondement des enseignements toltèques que Castenada fut le premier à faire découvrir au grand public. Don Miguel révèle ici 4 clés simples pour transformer sa vie et ses relations, tirées de la sagesse toltèque. Leur application au quotidien permet de transformer rapidement notre vie en une expérience de liberté, de vrai bonheur et d'amour.	5	1	https://images-na.ssl-images-amazon.com/images/I/81jfJUEh2AL.jpg
\.


--
-- Name: book_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.book_id_seq', 6, true);


--
-- Data for Name: borrowed_book; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.borrowed_book (user_id, book_id, return_date, extended, returned) FROM stdin;
2	3	2018-06-23	f	f
1	5	2014-04-09	t	t
1	2	2018-08-17	f	f
1	1	2015-07-06	t	t
1	6	2018-07-04	f	f
\.


--
-- Data for Name: genre; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.genre (id, name) FROM stdin;
1	Fantasy
2	Jeunesse
3	Fiction utopique et dystopique
4	Roman
5	Essai
\.


--
-- Name: genre_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.genre_id_seq', 5, true);


--
-- Data for Name: publisher; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.publisher (id, name) FROM stdin;
1	Pocket
2	Gallimard
3	Jouvence
\.


--
-- Name: publisher_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.publisher_id_seq', 3, true);


--
-- Data for Name: user_account; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_account (id, email, password, first_name, last_name, birthday, postal_address, tel, level_id) FROM stdin;
4	manager@kylibrary.fr	$2a$10$ynKx16tLp7N28l3zymlW0O349BBx3gS0478KCKaxxPeOQi.m3GByq	Manager	Yan	1983-05-19	18 rue de Bel Air 47000 Agen	0619785454	2
14	luc@delaval.fr	\N	Luc	Delaval	1986-05-15	10 rue de Belle Ville 75011 Paris	0126578248	3
1	lou@lou.fr	$2a$12$UMLERoIeZukh.tcnpeOUreZpZ5hBrll0YECkf9dYLqmCOYZVO6ohq	Lou	Lou	2008-05-15	10 rue de la paix 75011 Paris	0600120012	3
3	admin@kylibrary.fr	$2a$10$nW7UOJuTiqP6v7.oeWZk7eR4OSHpVfmtDeFhwnXmnE3i32ZKwCBFa	Admin	Yan	1983-05-19	18 rue de Bel Air 47000 Agen	0619785454	1
2	nslr@riseup.net	$2y$10$oWX4QwiWhMsSsJhOwj0cJ.LHkUz8sXhcBxsQ4/TmsBZRQlEJR1arS	Yan	Ky	1980-06-20	6 rue Laborde	0678542569	3
\.


--
-- Name: user_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_account_id_seq', 14, true);


--
-- Data for Name: user_level; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_level (id, label) FROM stdin;
1	user
2	manager
3	admin
\.


--
-- Name: user_level_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_level_id_seq', 3, true);


--
-- Name: author author_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);


--
-- Name: book book_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_id_pk PRIMARY KEY (id);


--
-- Name: genre genre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);


--
-- Name: publisher publisher_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publisher
    ADD CONSTRAINT publisher_pkey PRIMARY KEY (id);


--
-- Name: book uk_2rqqpgnve6gu3ar7prin5qm0i; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT uk_2rqqpgnve6gu3ar7prin5qm0i UNIQUE (isbn);


--
-- Name: user_account user_account_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_account
    ADD CONSTRAINT user_account_pkey PRIMARY KEY (id);


--
-- Name: user_level user_level_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_level
    ADD CONSTRAINT user_level_pkey PRIMARY KEY (id);


--
-- Name: book author_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT author_id_fk FOREIGN KEY (author_id) REFERENCES public.author(id);


--
-- Name: borrowed_book book_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.borrowed_book
    ADD CONSTRAINT book_id_fk FOREIGN KEY (book_id) REFERENCES public.book(id);


--
-- Name: book genre_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT genre_id_fk FOREIGN KEY (genre_id) REFERENCES public.genre(id);


--
-- Name: book publisher_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT publisher_id_fk FOREIGN KEY (publisher_id) REFERENCES public.publisher(id);


--
-- Name: borrowed_book user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.borrowed_book
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.user_account(id);


--
-- Name: user_account user_level_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_account
    ADD CONSTRAINT user_level_fk FOREIGN KEY (level_id) REFERENCES public.user_level(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

