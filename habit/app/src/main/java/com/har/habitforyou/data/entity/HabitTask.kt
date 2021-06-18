package com.har.habitforyou.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = HabitTask.TABLE_NAME,
        indices = [Index(value = ["id"], unique = true)])
data class HabitTask(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val title: String,
        val description: String,
        @ColumnInfo(name = "create_on") val createOn: Long,
        @ColumnInfo(name = "alarm_at") val alarmAt: Long
) {
    companion object {
        const val TABLE_NAME = "habit_tasks"
    }
}