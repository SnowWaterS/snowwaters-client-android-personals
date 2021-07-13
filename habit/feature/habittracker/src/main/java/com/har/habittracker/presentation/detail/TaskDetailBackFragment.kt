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
import com.har.habittracker.databinding.FragmentTaskDetailBackBinding

class TaskDetailBackFragment: Fragment() {

    private val args: TaskDetailBackFragmentArgs by navArgs()

    private var _binding: FragmentTaskDetailBackBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("FragmentTaskDetailBackBinding is null")

    private val viewModel: TaskDetailFrontFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_detail_back, container, false)

        viewModel.fetch(args.taskId)

        return binding.root
    }
}