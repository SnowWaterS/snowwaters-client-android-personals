package com.har.habitforyou.data.dao

import androidx.room.*
import com.har.habittracker.domain.model.HabitTask

@Dao
interface HabitTaskDao {

    @Query("SELECT * FROM `habit_tasks`")
    fun getAll(): List<HabitTask>

    @Query("SELECT * FROM `habit_tasks` WHERE id = :id")
    fun getHabitTaskById(id: Long): HabitTask

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(habitTask: HabitTask)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg habitTasks: HabitTask)

    @Update
    fun update(habitTask: HabitTask)

    @Update
    fun updateAll(vararg habitTasks: HabitTask)

    @Delete
    fun delete(habitTask: HabitTask)

}