package com.har.habittracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.har.habittracker.data.dao.HabitTaskDao
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.model.HabitTaskLog

@Database(
    entities = [HabitTask::class, HabitTaskLog::class],
    views = [],
    version = 1
)
abstract class HabitTrackerDatabase : RoomDatabase() {
    abstract fun habitTaskDao(): HabitTaskDao

    companion object {
        private var INSTANCE: HabitTrackerDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): HabitTrackerDatabase {
            val createdInstance = INSTANCE
            if (createdInstance != null) return createdInstance

            synchronized(this) {
                val createNewInstance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitTrackerDatabase::class.java,
                    "HabitTracker.db"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = createNewInstance
                return createNewInstance
            }
        }
    }
}