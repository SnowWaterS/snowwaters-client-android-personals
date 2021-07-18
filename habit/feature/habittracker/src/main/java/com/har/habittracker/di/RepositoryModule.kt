package com.har.habittracker.di

import com.har.habittracker.data.dao.HabitTaskDao
import com.har.habittracker.data.repository.HabitTaskRepository
import com.har.habittracker.domain.repository.IHabitTaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHabitTaskRepository(habitTaskDao: HabitTaskDao): IHabitTaskRepository = HabitTaskRepository(habitTaskDao)
}