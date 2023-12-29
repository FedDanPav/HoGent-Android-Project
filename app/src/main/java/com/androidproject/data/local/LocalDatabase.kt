package com.androidproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androidproject.data.local.dao.GenreDao
import com.androidproject.data.local.dao.MovieDao
import com.androidproject.data.local.dao.MovieToGenreDao
import com.androidproject.data.local.entity.GenreEntity
import com.androidproject.data.local.entity.MovieEntity
import com.androidproject.data.local.entity.MovieToGenreEntity

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,
        MovieToGenreEntity::class
               ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun movieToGenreDao(): MovieToGenreDao

    companion object {
        @Volatile
        private var INSTANCE : LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
