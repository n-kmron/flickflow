package g58008.mobg5.model

import android.content.Context
import android.util.Log
import g58008.mobg5.database.MovieDatabase
import g58008.mobg5.database.MovieItem

object Repository {

    private var database: MovieDatabase? = null

    fun initDatabase(context: Context) {
        if (database == null) database = MovieDatabase.getInstance(context)
    }

    suspend fun addFavourite(movie: String, user: String) {
        Log.d("REPOSITORY", "addFavourite for $user : $movie")
        database?.let { theDatabase ->
            val movieItem = MovieItem(user = user, movie = movie)
            theDatabase.movieDao().insertMovie(movieItem)
        }
    }

    suspend fun getFavourites(user: String): List<MovieItem> {
        database?.let { theDatabase ->
            Log.d("REPOSITORY", "getFavourites for $user : ${theDatabase.movieDao().getFavourites(user)}")
            return theDatabase.movieDao().getFavourites(user)
        }
        return emptyList()
    }

    suspend fun deleteFavourite(movie: String, user: String) {
        Log.d("REPOSITORY", "deleteFavourite: $movie")
        database?.let { theDatabase ->
            theDatabase.movieDao().deleteFavourite(user, movie)
        }
    }
}