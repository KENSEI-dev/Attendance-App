package com.attendance.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HolidayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(holiday: HolidayEntity)

    @Query("DELETE FROM holidays WHERE date = :date")
    suspend fun delete(date: String)

    @Query("SELECT * FROM holidays WHERE date = :date LIMIT 1")
    suspend fun findByDate(date: String): HolidayEntity?

    @Query("SELECT * FROM holidays ORDER BY date DESC")
    fun getAll(): Flow<List<HolidayEntity>>
}
