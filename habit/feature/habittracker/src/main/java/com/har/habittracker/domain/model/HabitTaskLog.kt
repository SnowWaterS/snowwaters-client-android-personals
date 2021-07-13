package com.har.habittracker.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.*

@Entity(tableName = HabitTaskLog.TABLE_NAME,
        indices = [Index(value = ["habit_task_id"])],
        foreignKeys = [ForeignKey(
                entity = HabitTask::class,
                childColumns = ["habit_task_id"],
                parentColumns = ["id"],
                onDelete = ForeignKey.CASCADE)])
data class HabitTaskLog(
        @ColumnInfo(name = "habit_task_id") val habitTaskId: Int,
        @ColumnInfo(name = "check_date") val checkDate: Date
) {
    companion object {
        const val TABLE_NAME = "habit_task_logs"
    }
}
