package com.har.habittracker.presentation.list.component

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.presentation.detail.TaskDetailFrontFragment
import javax.inject.Inject

class TaskDetailFragmentStateAdapter @Inject constructor(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val taskDetailList: MutableList<HabitTask> = mutableListOf()

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun createFragment(position: Int): Fragment {
        return TaskDetailFrontFragment()
    }

    fun setTaskDetailList(newTaskDetailList: List<HabitTask>) {
        taskDetailList.clear()
        taskDetailList.addAll(newTaskDetailList)
        notifyDataSetChanged()
    }
}