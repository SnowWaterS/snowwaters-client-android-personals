package com.har.habbittracker.presentation

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.har.habbittracker.databinding.ActivityHabbitTrackerBinding
import com.har.habbittracker.presentation.shared.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class HabbitTrackerActivity: AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()
    private var _binding: ActivityHabbitTrackerBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("ActivityHabbitTrackerBinding is null")

}