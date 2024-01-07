package g58008.mobg5.database

import androidx.room.ColumnInfo

data class MovieItemId(

    @ColumnInfo(name = "user")
    val user: String,

    @ColumnInfo(name = "movie")
    val movie: String
)