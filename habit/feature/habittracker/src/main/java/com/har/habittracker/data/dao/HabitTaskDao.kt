package com.har.habittracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.har.habittracker.domain.model.HabitTask

@Dao
interface HabitTaskDao {

    @Query("SELECT * FROM habit_tasks")
    fun getLiveAllDataHabitTask(): LiveData<List<HabitTask>>

    @Query("SELECT * FROM habit_tasks WHERE id = :taskId")
    fun getLiveDataHabitTaskById(taskId: Int): LiveData<HabitTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabitTask(task: HabitTask)

    @Update
    fun updateHabitTask(task: HabitTask)

    @Query("DELETE FROM habit_tasks WHERE id = :taskId")
    fun deleteHabitTaskById(taskId: Int)

    @Delete
    fun deleteHabitTask(task: HabitTask)
}