# FlickFlow

Ce dépôt contient les sources du projet `Cameron`.

## WARNING : update 

Pour le bien de mon projet, j'ai du changer d'API. Pour les détails de cette API, ils ont été mis à jour dans la section `Backend` de ce README. Ce changement a peu de répercussions mis à part que je ne peux pas afficher d'images du film comme vous me l'avez suggéré. J'espère que vous comprenez ce changement.

## Description

L'application permet de trouver des films actuellement classés dans le Box Office 200 aléatoirement. Si l'utilisateur possède un compte sur l'application, il peut partager et enregister le film à ses favoris.

## Utilisation

Se connecter avec le service supabase donné par l'école et se laisser guider par l'interface intuitive.

## Persistance des données

L'application conserve un historique des films proposés si ils sont ajoutés dans les favoris. Cet historique est persisté dans la base de données locale (ROOM) de l'application.

Les données relatives aux comptes utilisateurs proviennent du serveur proposé par l'ESI : <https://dnsrivnxleeqdtbyhftv.supabase.co/>

## Backend

L'application mobile appelle via des services web le backend <RapidAPI MoviesDatabase>. Les sources sont disponibles sur le dépôt <https://rapidapi.com/SAdrian/api/moviesdatabase/details>.

## Code 
- compile SDK 34
- Pattern MVVM
- Customisation du thème (shape, type, color) avec MaterialTheme
- Utilisation d'une Topbar et BottomBar avec Scaffold
- Point d'entrée = App.kt
- Navigation avec NavController et NavHost
- Aucun string en dur (utilisation de strings.xml et dimens.xml)
- Aucun magic number
- Gestion de la rotation d'écran (aucune information ne se perd et tout est gardé en l'état, seulement j'ai choisi de désactiver la BottomBar pour une meilleure expérience utilisateur)
- Gestion des lifecycles

## Implémentation
- Les favoris sont stockés en DB locale (user, movie_id)
- Les favoris sont affichés avec une Lazy Row qui reprend les tuples récupérés en DB
- Pour charger un favoris, un call API est lancé à partir de l'id stocké en DB pour récupérer les informations sur le film.
- Pas de bouton navigation Up car tous les écrans sont accessibles depuis la BottomBar (l'écran détail étant simplement une extension du HomeScreen)
- AppViewModel et MovieViewModel sont des singletons (avec object et companion object) pour être récupérés et utilisés par plusieurs composables et conservant les mêmes données.

## Features :

**Roulette de Films :**
  - L'utilisateur peut appuyer sur un bouton pour lancer la "roulette" et obtenir une recommandation aléatoire de film disponible actuellement dans le BoxOffice 200.

**Détails du Film :**
  - Affiche les détails du film recommandé, tels que le titre, la date de sortie, la description, le rating des utilisateurs etc.

**Bouton de Rejouer :**
  - Permet à l'utilisateur de relancer la roulette pour découvrir un autre film aléatoire.

**Ajouter à ses favoris**
  - Permet à l'utilisateur d'ajouter le film donné dans ses favoris afin de le retrouver plus facilement.

**Bouton de Partage :**
  - Donne à l'utilisateur la possibilité de partager le film recommandé avec ses amis via des applications de messagerie ou sur les réseaux. Il s'agit cependant d'une feature rudimentaire qui pourra être améliorée.

## Auteur

**Cameron Noupoue** g58008
