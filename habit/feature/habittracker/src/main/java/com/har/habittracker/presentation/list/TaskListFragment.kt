package com.har.habittracker.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.har.habittracker.R
import com.har.habittracker.databinding.FragmentTaskListBinding

class TaskListFragment: Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("FragmentTaskListBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false)

        return binding.root
    }
}