# Projet MOBG5

Ce dépôt contient les sources du projet `Cameron`.

## Description

L'application permet de trouver les séances des films disponibles autour de sa position. Une fois la séance terminée, si l'utilisateur possède un compte sur l'application, il peut critiquer et noter le film.

## Utilisation
Dans cette première remise, pour se connecter, il faut utiliser l'email `abc@he2b.be` avec un mot de passe quelconque.

## Persistance des données

L'application conserve un historique des personnes proposées. Cet historique est persisté dans la base de données locale de l'application.

Les données relatives aux comptes utilisateurs, aux personnes et aux musiques sont persistées via firebase : <url du projet firebase>

## Backend

L'application mobile appelle via des services web le backend <nom du backend>. Les sources sont disponibles sur le dépôt <url du dépôt du backend>.

## Code 
- Pattern MVVM
- Customisation du thème (shape, type, color) avec MaterialTheme
- Utilisation d'une Topbar avec Scaffold
- Point d'entrée = App.kt
- Navigation avec NavController et NavHost
- Aucun string en dur (utilisation de strings.xml et dimens.xml)
- Aucun magic number
- Gestion de la rotation d'écran
- Gestion des lifecycles
## Service rest

Pour collecter les données relatives aux films, des appels au service rest suivant sont effectués : https://www.themoviedb.org/documentation/api

## Auteur

**Cameron Noupoue** g58008
