package g58008.mobg5.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieItem)

    @Query("SELECT * FROM movie_favourite WHERE user = :user")
    suspend fun getFavourites(user: String): List<MovieItem>

    @Query("DELETE FROM movie_favourite WHERE user = :user AND movie = :movie")
    suspend fun deleteFavourite(user: String, movie: String)
}