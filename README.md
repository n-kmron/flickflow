# FlickFlow

Ce dépôt contient les sources du projet `Cameron`.

## WARNING : update 

Pour le bien de mon projet, j'ai du changer d'API. Pour les détails de cette API, ils ont été mis à jour dans la section `Backend` de ce README. Ce changement a des répercussions concernant les détails donnés sur le film : en effet, il me permet seulement de me donner le classement Box Office ainsi que la date de sortie. Je n'afficherai donc pas le score donnés par les consommateurs ainsi qu'une description comme donné sur le diagramme. J'espère que vous comprenez ce changement.

## Description

L'application permet de trouver des films disponibles sur Netflix aléatoirement. Si l'utilisateur possède un compte sur l'application, il peut partager et enregister le film.

## Utilisation
Se connecter avec le service supabase donné par l'école et se laisser guider par l'interface intuitive.

## Persistance des données

L'application conserve un historique des films proposés si ils sont ajoutés dans les favoris. Cet historique est persisté dans la base de données locale de l'application.

Les données relatives aux comptes utilisateurs proviennent du serveur proposé par l'ESI : <https://dnsrivnxleeqdtbyhftv.supabase.co/>

## Backend

L'application mobile appelle via des services web le backend <RapidAPI MoviesDatabase>. Les sources sont disponibles sur le dépôt <https://rapidapi.com/SAdrian/api/moviesdatabase/details>.

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

## Features :

**Roulette de Films :**
  - L'utilisateur peut appuyer sur un bouton pour lancer la "roulette" et obtenir une recommandation aléatoire de film disponible actuellement dans le BoxOffice 200.

**Détails du Film :**
  - Affiche les détails du film recommandé, tels que le titre, la date de sortie, le classement box office, etc.

**Bouton de Rejouer :**
  - Permet à l'utilisateur de relancer la roulette pour découvrir un autre film aléatoire.

**Bouton de Partage :**
  - Donne à l'utilisateur la possibilité de partager le film recommandé avec ses amis via des applications de messagerie ou sur les réseaux.

## Auteur

**Cameron Noupoue** g58008
