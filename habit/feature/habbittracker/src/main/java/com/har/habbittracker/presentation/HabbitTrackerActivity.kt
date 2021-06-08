package com.har.habbittracker.presentation

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.har.habbittracker.presentation.shared.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabbitTrackerActivity: AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()
}