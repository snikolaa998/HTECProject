package com.example.htecproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.htecproject.model.Posts

@Database(entities = [Posts::class], version = 1, exportSchema = false)

abstract class PostDb() : RoomDatabase() {
    abstract fun postDao(): PostDao
    companion object {
        private var INSTANCE: PostDb? = null
        internal fun getDatabase(context: Context): PostDb? {
            if (INSTANCE == null) {
                synchronized(PostDb::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            PostDb::class.java,
                            "posts_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}