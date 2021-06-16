package com.har.habittracker.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.har.habittracker.R
import com.har.habittracker.databinding.FragmentCalendarBinding

class CalendarFragment: Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("FragmentCalendarBinding is null")

    private val viewModel: CalendarFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

}