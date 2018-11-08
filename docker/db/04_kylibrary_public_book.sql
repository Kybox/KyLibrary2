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
    CONSTRAINT author_id_fk FOREIGN KEY (author_id) REFERENCES public.author (id),
    CONSTRAINT publisher_id_fk FOREIGN KEY (publisher_id) REFERENCES public.publisher (id),
    CONSTRAINT genre_id_fk FOREIGN KEY (genre_id) REFERENCES public.genre (id)
);
CREATE UNIQUE INDEX uk_2rqqpgnve6gu3ar7prin5qm0i ON public.book (isbn);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies) VALUES (5, '978-2266283038', 'Le meilleur des mondes', 5, 1, '2017-08-17', 'Voici près d''un siècle, dans d''étourdissantes visions, Aldous Huxley imagine une civilisation future jusque dans ses rouages les plus surprenants : un État Mondial, parfaitement hiérarchisé, a cantonné les derniers humains " sauvages " dans des réserves. La culture in vitro des fœtus a engendré le règne des " Alphas ", génétiquement déterminés à être l''élite dirigeante. Les castes inférieures, elles, sont conditionnées pour se satisfaire pleinement de leur sort. Dans cette société où le bonheur est loi, famille, monogamie, sentiments sont bannis. Le meilleur des mondes est possible. Aujourd''hui, il nous paraît même familier...', 3, 1, 'https://images-na.ssl-images-amazon.com/images/I/511KW0qCjCL.jpg', 3);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies) VALUES (3, '978-2072730030', '1984', 3, 2, '2018-05-24', 'Année 1984 en Océanie. 1984 ? C''est en tout cas ce qu''il semble à Winston, qui ne saurait toutefois en jurer. Le passé a été oblitéré et réinventé, et les événements les plus récents sont susceptibles d''être modifiés. Winston est lui-même chargé de récrire les archives qui contredisent le présent et les promesses de Big Brother. Grâce à une technologie de pointe, ce dernier sait tout, voit tout. Il n''est pas une âme dont il ne puisse connaître les pensées. On ne peut se fier à personne et les enfants sont encore les meilleurs espions qui soient. Liberté est Servitude. Ignorance est Puissance. Telles sont les devises du régime de Big Brother. La plupart des Océaniens n''y voient guère à redire, surtout les plus jeunes qui n''ont pas connu l''époque de leurs grands-parents et le sens initial du mot «libre». Winston refuse cependant de perdre espoir. Il entame une liaison secrète et hautement dangereuse avec l''insoumise Julia et tous deux vont tenter d''intégrer la Fraternité, une organisation ayant pour but de renverser Big Brother. Mais celui-ci veille... Le célèbre et glaçant roman de George Orwell se redécouvre dans une nouvelle traduction, plus directe et plus dépouillée, qui tente de restituer la terreur dans toute son immédiateté mais aussi les tonalités nostalgiques et les échappées lyriques d''une œuvre brutale et subtile, équivoque et génialement manipulatrice.', 3, 1, 'https://images-na.ssl-images-amazon.com/images/I/51rr0JcuqjL.jpg', 2);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies) VALUES (2, '978-2070612758', 'Le petit prince', 2, 2, '2007-03-15', 'Le premier soir, je me suis donc endormi sur le sable à mille milles de toute terre habitée. J''étais bien plus isolé qu''un naufragé sur un radeau au milieu de l''océan. Alors, vous imaginez ma surprise, au lever du jour, quand une drôle de petite voix m''a réveillé. Elle disait : “S''il vous plaît... dessine-moi un mouton !” J''ai bien regardé. Et j''ai vu ce petit bonhomme tout à fait extraordinaire qui me considérait gravement...', 2, 1, 'https://images-na.ssl-images-amazon.com/images/I/71lWtUcX47L.jpg', 4);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies) VALUES (1, '978-2266232999', 'Le Seigneur des Anneaux', 1, 1, '2012-11-22', 'La Terre est peuplée d''innombrables créatures étranges. Les Hobbits, apparentés à l''homme, mais proches également des Elfes et des Nains, vivent en paix au nord-ouest de l''Ancien Monde, dans la Comté. Paix précaire et menacée, cependant, depuis que Bilbon Sacquet a dérobé au monstre Gollum l''anneau de Puissance jadis forgé par Sauron de Mordor. Car cet anneau est doté d''un pouvoir immense et maléfique. Il permet à son détenteur de se rendre invisible et lui confère une autorité sans limite sur les possesseurs des autres anneaux. Bref, il fait de lui le Maître du Monde. C''est pourquoi Sauron s''est juré de reconquérir l''anneau par tous les moyens. Déjà ses Cavaliers Noirs rôdent aux frontières de la Comté…. Ainsi débute la trilogie du Seigneur des anneaux.', 1, 1, 'https://images-na.ssl-images-amazon.com/images/I/41ngIhV8tKL.jpg', 2);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies) VALUES (4, '978-2070368792', 'Les faux-monnayeurs', 4, 2, '1972-06-29', 'Depuis quelque temps, des pièces de fausse monnaie circulent. J''en suis averti. Je n''ai pas encore réussi à découvrir leur provenance. Mais je sais que le jeune Georges - tout naïvement je veux le croire - est un de ceux qui s''en servent et les mettent en circulation. Ils sont quelques-uns, de l''âge de votre neveu, qui se prêtent à ce honteux trafic. Je ne mets pas en doute qu''on abuse de leur innocence et que ces enfants sans discernement ne jouent le rôle de dupes entre les mains de quelques coupables aînés.', 4, 1, 'https://images-na.ssl-images-amazon.com/images/I/81MCnnWKvNL.jpg', 1);
INSERT INTO public.book (id, isbn, title, author_id, publisher_id, publishdate, summary, genre_id, available, cover, nb_copies) VALUES (6, '978-2889116546', 'Les quatre accords toltèques', 6, 3, '2016-01-08', 'Découvrez ou redécouvrez Les quatre accords toltèques, et prenez comme des millions de lecteurs en France et à travers le monde, la voie de la liberté personnelle.
Dans ce livre, Don Miguel révèle la source des croyances limitatrices qui nous privent de joie et créent des souffrances inutiles. Il montre en des termes très simples comment on peut se libérer du conditionnement collectif - le "rêve de la planète", basé sur la peur - afin de retrouver la dimension d''amour inconditionnel qui est à notre origine et constitue le fondement des enseignements toltèques que Castenada fut le premier à faire découvrir au grand public. Don Miguel révèle ici 4 clés simples pour transformer sa vie et ses relations, tirées de la sagesse toltèque. Leur application au quotidien permet de transformer rapidement notre vie en une expérience de liberté, de vrai bonheur et d''amour.', 5, 1, 'https://images-na.ssl-images-amazon.com/images/I/81jfJUEh2AL.jpg', 6);