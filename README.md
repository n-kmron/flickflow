# FlickFlow

Ce dépôt contient les sources du projet `Cameron`.

## Description

L'application permet de trouver des films disponibles sur Netflix aléatoirement. Si l'utilisateur possède un compte sur l'application, il peut partager et enregister le film.

## Utilisation
Dans cette première remise, pour se connecter, il faut utiliser un email `he2b.be`

## Persistance des données

L'application conserve un historique des films proposés si ils sont ajoutés dans les favoris. Cet historique est persisté dans la base de données locale de l'application.

Les données relatives aux comptes utilisateurs proviennent du serveur proposé par l'ESI : <https://dnsrivnxleeqdtbyhftv.supabase.co/>

## Backend

L'application mobile appelle via des services web le backend <Realgood Netflix Roulette>. Les sources sont disponibles sur le dépôt <https://reelgood.com/movies/roulette/netflix>.

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
  - L'utilisateur peut appuyer sur un bouton pour lancer la "roulette" et obtenir une recommandation aléatoire de film disponible sur Netflix.

**Détails du Film :**
  - Affiche les détails du film recommandé, tels que le titre, la description, la note, etc.

**Bouton de Rejouer :**
  - Permet à l'utilisateur de relancer la roulette pour découvrir un autre film aléatoire.

**Bouton de Partage :**
  - Donne à l'utilisateur la possibilité de partager le film recommandé avec ses amis via des applications de messagerie ou sur les réseaux 


## Auteur

**Cameron Noupoue** g58008
