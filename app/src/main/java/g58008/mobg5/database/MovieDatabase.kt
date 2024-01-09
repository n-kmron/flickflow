package g58008.mobg5.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieItem::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDAO

    /*
    When a context is needed, it can be passed as a parameter using an activity,
    or one can use Activity.applicationContext, which returns the application context from any activity context.
     */
    companion object {
        private const val DATABASE_NAME = "movie_db"
        private var sInstance : MovieDatabase? = null
        fun getInstance(context: Context) : MovieDatabase {
            if (sInstance == null) {
                val dbBuilder = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DATABASE_NAME
                )
                sInstance = dbBuilder.build()
                //context.deleteDatabase(DATABASE_NAME)
            }
            return sInstance!!
        }
    }
}
