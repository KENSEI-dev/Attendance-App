package com.attendance.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(subject: SubjectEntity): Long

    @Delete
    suspend fun delete(subject: SubjectEntity)

    @Query("SELECT * FROM subjects ORDER BY name ASC")
    fun getAll(): Flow<List<SubjectEntity>>

    @Query("SELECT * FROM subjects WHERE id = :id")
    suspend fun getById(id: Long): SubjectEntity?

    /**
     * Used during sync merge: subjects are matched across devices by name
     * (see data/sync/SyncModels.kt), since Room's autoincrement id is only
     * meaningful within a single device's database.
     */
    @Query("SELECT * FROM subjects WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): SubjectEntity?
}
