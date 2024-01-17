# FlickFlow

The application allows users to discover movies currently ranked in the top 200 Box Office randomly. If the user has an account on the application, they can share and save the movie to their favorites.

## Setup

You have to setup api key for the movies database [movie database](https://rapidapi.com/SAdrian/api/moviesdatabase/details) in `MovieService.kt`

You have to put your own api service for connection in `AuthService.kt`

## Data Persistence

The application keeps a history of suggested movies if they are added to favorites. This history is persisted in the local database (ROOM) of the application.

## Backend

The mobile application makes web service calls to the [RapidAPI MoviesDatabase](https://rapidapi.com/SAdrian/api/moviesdatabase/details). The sources are available in the repository.

## Code 
- Compile SDK 34
- MVVM Pattern
- Customization of the theme (shape, type, color) with MaterialTheme
- Use of Topbar and BottomBar with Scaffold
- Entry point = App.kt
- Navigation with NavController and NavHost
- No hard-coded strings (use of strings.xml and dimens.xml)
- No magic numbers
- Handling screen rotation (no data loss, everything is retained; however, the BottomBar is disabled for a better user experience)
- Lifecycle management

## Implementation
- Favorites are stored in the local DB (user, movie_id)
- Favorites are displayed with a Lazy Row that retrieves tuples from the DB
- To load a favorite, an API call is made using the stored ID in the DB to fetch movie information.
- AppViewModel and MovieViewModel are singletons (using object and companion object) to be retrieved and used by multiple composables while maintaining the same data.

## Features:

**Movie Roulette:**
  - Users can press a button to start the "roulette" and get a random movie recommendation currently available in the Box Office 200.

**Movie Details:**
  - Displays details of the recommended movie, such as title, release date, description, user rating, etc.

**Replay Button:**
  - Allows users to rerun the roulette to discover another random film.

**Add to Favorites:**
  - Allows users to add the given movie to their favorites for easy retrieval.

**Share Button:**
  - Gives users the option to share the recommended movie with friends via messaging apps or on social networks. This feature is rudimentary and can be further improved.

## Author

* Cameron Noupoue

## Credits 

Project devised and created during my studies at the École Supérieure d'Informatique (ESI), Brussels.

