package com.har.habittracker.presentation

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.har.habittracker.databinding.ActivityHabbitTrackerBinding
import com.har.habittracker.presentation.shared.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class HabbitTrackerActivity: AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()
    private var _binding: ActivityHabbitTrackerBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("ActivityHabbitTrackerBinding is null")

}