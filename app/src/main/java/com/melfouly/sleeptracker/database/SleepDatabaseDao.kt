package com.melfouly.sleeptracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepDatabaseDao {
    @Insert
    suspend fun insert(night: SleepNight)

    @Update
    suspend fun update(night: SleepNight)

    @Query("select * from daily_sleep_quality_table where nightId = :key")
    suspend fun get(key: Long): SleepNight?

    @Query("delete from daily_sleep_quality_table")
    suspend fun clear()

    @Query("select * from daily_sleep_quality_table order by nightId desc")
    fun getAllNights(): LiveData<List<SleepNight>>

    @Query("select * from daily_sleep_quality_table order by nightId desc limit 1")
    suspend fun getTonight(): SleepNight?

    @Query("select * from daily_sleep_quality_table where nightId = :key")
    fun getNightWithId(key: Long): LiveData<SleepNight>
}