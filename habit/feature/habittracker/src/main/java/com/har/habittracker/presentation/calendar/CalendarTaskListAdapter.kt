package com.har.habittracker.presentation.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.har.habittracker.databinding.ItemCalendarTaskBinding

class CalendarTaskListAdapter: RecyclerView.Adapter<CalendarTaskListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarTaskListViewHolder {
        return CalendarTaskListViewHolder(ItemCalendarTaskBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CalendarTaskListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}

class CalendarTaskListViewHolder(val binding: ItemCalendarTaskBinding): RecyclerView.ViewHolder(binding.root) {

}