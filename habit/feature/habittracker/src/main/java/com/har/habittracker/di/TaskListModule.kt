package com.har.habittracker.di

import androidx.fragment.app.Fragment
import com.har.habittracker.presentation.list.component.TaskDetailFragmentStateAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object TaskListModule {

    @Provides
    @FragmentScoped
    fun provideTaskDetailFragmentStateAdapter(fragment: Fragment): TaskDetailFragmentStateAdapter {
        return TaskDetailFragmentStateAdapter(fragment)
    }

}