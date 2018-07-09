## KyLibrary
Parcours : OpenclassRooms - DA Java/JEE

Projet 7 : Développez le nouveau système d’information d’une bibliothèque

### Architecture

#### Maven

- [ webservice ] : SOAP Web service
- [ batch ] : Système d’envoi d’e-mails récurent
- [ client ] : Application web

### Frameworks et API

- Spring Framework 5.0.6
- Spring Security 5.0.5
- Spring Batch 4.0.1
- Spring Data 2.0.7
- Hibernate 5.2.17
- Apache Struts 2.5.16
- Apache CXF 3.2.4
- Apache Log4J 2.11.0
- Javax Mail 1.6.1
- Bootstrap 3.3.7

### Persistence

- PostgreSQL 10.4

La modification des informations de connexion à la base de données doit être réalisée dans le fichier de configuration du serveur d’application.

### Description

##### Le web service propose :
- l’authentification d’un utilisateur
- la récupération des ouvrages emprunté par un utilisateur ainsi qu’un historique*
- la possibilité de prolonger la durée de l’emprunt d’un ouvrage*
- la consultation des différents ouvrages enregistrés en base de données
- la création d’un nouveau compte utilisateur
- d’enregistrer un retour d’ouvrage
- de récupérer la liste des ouvrages non rendus à temps

Info : Seul le web service peut se connecter à la base de données.

##### L’application web propose :
- la possibilité d’effectuer une recherche d’ouvrage aussi bien par titre que par auteurs ou genre
- une interface d’authentification
- une interface utilisateur avec le rappel des informations du compte ainsi que la possibilité d’étendre le délai d’un emprunt

##### Le batch :
Le batch effectue un envoi d’e-mail récurrent en se connectant, en mode administrateur, au web service afin de récupérer la liste des ouvrage non rendus à temps.