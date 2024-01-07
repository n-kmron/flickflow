package g58008.mobg5.database

import androidx.room.Entity

@Entity(tableName = "movie_favourite", primaryKeys = ["user", "movieId"])
data class MovieItem (
    val user: String,
    val movieId : String,
    val movieTitle : String
)