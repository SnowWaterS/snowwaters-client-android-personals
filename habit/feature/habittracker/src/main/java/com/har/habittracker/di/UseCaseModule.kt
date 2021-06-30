package com.har.habittracker.di

import com.har.habittracker.domain.repository.IHabitTaskLogRepository
import com.har.habittracker.domain.repository.IHabitTaskRepository
import com.har.habittracker.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetHabitTaskUseCase(repository: IHabitTaskRepository): GetHabitTaskUseCase = GetHabitTaskUseCase(repository)

    @Provides
    fun provideAddHabitTaskUseCase(repository: IHabitTaskRepository): AddHabitTaskUseCase = AddHabitTaskUseCase(repository)

    @Provides
    fun provideUpdateHabitTaskUseCase(repository: IHabitTaskRepository): UpdateHabitTaskUseCase = UpdateHabitTaskUseCase(repository)

    @Provides
    fun provideDeleteHabitTaskUseCase(repository: IHabitTaskRepository): DeleteHabitTaskUseCase = DeleteHabitTaskUseCase(repository)

    @Provides
    fun provideGetHabitTaskLogUseCase(repository: IHabitTaskLogRepository): GetHabitTaskLogUseCase = GetHabitTaskLogUseCase(repository)

    @Provides
    fun provideCheckHabitTaskDoneUseCase(repository: IHabitTaskLogRepository): CheckHabitTaskDoneUseCase = CheckHabitTaskDoneUseCase(repository)

    @Provides
    fun provideCancelHabitTaskLogUseCase(repository: IHabitTaskLogRepository): CancelHabitTaskLogUseCase = CancelHabitTaskLogUseCase(repository)

}