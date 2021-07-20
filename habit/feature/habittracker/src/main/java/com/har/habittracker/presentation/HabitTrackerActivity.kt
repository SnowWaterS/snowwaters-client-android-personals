package com.har.habittracker.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.har.habittracker.R
import com.har.habittracker.presentation.addition.NewTaskDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitTrackerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habbit_tracker)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_new_task -> {
                val dialog = NewTaskDialog()
                dialog.show(supportFragmentManager, "NewTaskDialog")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}