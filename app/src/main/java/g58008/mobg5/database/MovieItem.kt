package g58008.mobg5.database

import androidx.room.Entity

@Entity(tableName = "movie_favourite", primaryKeys = ["user", "movie"])
data class MovieItem (
    val user: String,
    val movie : String
)