# Mini Projet [sprint 03] - Backend

Spring Boot API Spring Boot du Mini Projet [sprint 03], consommée par le projet Angular.

## Outils

- Spring Boot 3.5.16, Java 17, Maven Wrapper
- MySQL fourni par XAMPP
- Spring Data JPA, Spring Data REST, API REST et JWT

## Base de données

Démarrer MySQL dans XAMPP. L'application utilise la base `mini_projet_sprint03`, le compte `root` et un mot de passe vide par défaut. Si votre compte MySQL a un mot de passe, définir la variable `DB_PASSWORD` avant le lancement.

## Lancement depuis Eclipse

Importer ce dossier avec **File > Import > Maven > Existing Maven Projects**, sélectionner `pom.xml`, puis exécuter `LaptopsApiApplication` en **Spring Boot App**. Le Maven Wrapper est également disponible avec `mvnw.cmd spring-boot:run`.

L'API écoute le port `8081` et est préfixée par `/produits`. Les opérations manuelles sont sous `/api`; Spring Data REST expose les laptops sous `/rest` et les types sous `/cat`.

Comptes de développement créés au premier lancement : `admin / 123` et `user / 123`.
