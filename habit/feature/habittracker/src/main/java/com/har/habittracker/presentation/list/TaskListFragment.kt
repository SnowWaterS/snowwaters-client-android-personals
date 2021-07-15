package com.har.habittracker.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.har.habittracker.R
import com.har.habittracker.databinding.FragmentTaskListBinding
import com.har.habittracker.presentation.calendar.CalendarFragmentDirections
import com.har.habittracker.presentation.list.component.TaskDetailFragmentStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding
        get() = _binding ?: throw NullPointerException("FragmentTaskListBinding is null")

    private val viewModel: TaskListFragmentViewModel by viewModels()

    @Inject
    lateinit var fragmentStateAdapter: TaskDetailFragmentStateAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false)
        binding.viewModel = viewModel
        binding.layAddNewTask.setOnClickListener {
            findNavController().navigate(CalendarFragmentDirections.actionCalendarFragmentToTaskListDialog())
        }
        binding.vpTaskList.adapter = fragmentStateAdapter
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.habitTaskList.observe(viewLifecycleOwner) {
            fragmentStateAdapter.setTaskDetailList(it)
        }
    }
}