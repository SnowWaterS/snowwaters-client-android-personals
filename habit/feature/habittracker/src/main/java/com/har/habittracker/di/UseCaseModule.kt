package com.har.habittracker.di

import com.har.habittracker.domain.repository.IHabitTaskLogRepository
import com.har.habittracker.domain.repository.IHabitTaskRepository
import com.har.habittracker.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetHabitTaskUseCase(repository: IHabitTaskRepository): GetHabitTaskUseCase = GetHabitTaskUseCase(repository)

    @Singleton
    @Provides
    fun provideAddHabitTaskUseCase(repository: IHabitTaskRepository): AddHabitTaskUseCase = AddHabitTaskUseCase(repository)

    @Singleton
    @Provides
    fun provideUpdateHabitTaskUseCase(repository: IHabitTaskRepository): UpdateHabitTaskUseCase = UpdateHabitTaskUseCase(repository)

    @Singleton
    @Provides
    fun provideDeleteHabitTaskUseCase(repository: IHabitTaskRepository): DeleteHabitTaskUseCase = DeleteHabitTaskUseCase(repository)

    @Singleton
    @Provides
    fun provideGetHabitTaskLogUseCase(repository: IHabitTaskLogRepository): GetHabitTaskLogUseCase = GetHabitTaskLogUseCase(repository)

    @Singleton
    @Provides
    fun provideCheckHabitTaskDoneUseCase(repository: IHabitTaskLogRepository): CheckHabitTaskDoneUseCase = CheckHabitTaskDoneUseCase(repository)

    @Singleton
    @Provides
    fun provideCancelHabitTaskLogUseCase(repository: IHabitTaskLogRepository): CancelHabitTaskLogUseCase = CancelHabitTaskLogUseCase(repository)

}