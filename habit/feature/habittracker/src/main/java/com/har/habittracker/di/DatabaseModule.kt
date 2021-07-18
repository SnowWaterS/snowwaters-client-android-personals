package com.har.habittracker.di

import android.content.Context
import com.har.habittracker.data.HabitTrackerDatabase
import com.har.habittracker.data.dao.HabitTaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideHabitTrackerDatabase(@ApplicationContext appContext: Context): HabitTrackerDatabase {
        return HabitTrackerDatabase.getInstance(appContext)
    }

    @Singleton
    @Provides
    fun provideHabitTaskDao(@ApplicationContext appContext: Context): HabitTaskDao {
        return HabitTrackerDatabase.getInstance(appContext).habitTaskDao()
    }

}