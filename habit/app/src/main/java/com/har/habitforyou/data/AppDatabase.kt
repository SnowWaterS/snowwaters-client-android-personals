package com.har.habitforyou.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.har.habitforyou.data.dao.HabitTaskDao
import com.har.habitforyou.data.entity.HabitTask

@Database(entities = [HabitTask::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun habitTaskDao(): HabitTaskDao

    companion object {

        private const val DB_NAME = "HabitForYou.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getInstacne(context: Context): AppDatabase {
            val createdInstance = INSTANCE
            if (createdInstance != null) return createdInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}