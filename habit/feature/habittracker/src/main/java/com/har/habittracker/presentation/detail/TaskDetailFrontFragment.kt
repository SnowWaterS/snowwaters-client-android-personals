package com.har.habittracker.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.har.habittracker.R
import com.har.habittracker.databinding.FragmentTaskDetailFrontBinding

class TaskDetailFrontFragment: Fragment() {

    private var _binding: FragmentTaskDetailFrontBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("FragmentTaskDetailFrontBinding is null")

    private val viewModel: TaskDetailFrontFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_detail_front, container, false)

        var taskId = 0
        var args = arguments
        args?.let {
            taskId = it.getInt(KEY_TASK_ID)
        }
        viewModel.fetch(taskId)
        return binding.root
    }

    companion object {

        const val KEY_TASK_ID = "key_task_id"

        @JvmStatic
        fun getInstance(taskId: Int): TaskDetailFrontFragment {
            val fragment = TaskDetailFrontFragment()
            val args = Bundle()
            args.putInt(KEY_TASK_ID, taskId)
            fragment.arguments = args
            return fragment
        }

    }

}