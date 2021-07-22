package com.har.habittracker.presentation.addition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.har.habittracker.R
import com.har.habittracker.databinding.DialogNewTaskBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewTaskDialog: DialogFragment() {

    private var _binding: DialogNewTaskBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("DialogNewTaskBinding is null")

    private val viewModel: NewTaskDialogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_new_task, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.layAddNewTask.setOnClickListener {
            dismissAllowingStateLoss()
        }

        return binding.root
    }
}