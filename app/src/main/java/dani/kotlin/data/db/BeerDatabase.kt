package dani.kotlin.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Beer::class], version = 1)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao

    companion object {
        @Volatile private var INSTANCE: BeerDatabase? = null
        private const val DATABASE_NAME = "beer_database"

        fun getDatabase(context: Context): BeerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context, BeerDatabase::class.java,
                    DATABASE_NAME).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}