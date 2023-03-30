package com.example.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.model.model.Difficulty
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PathDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(path: PathData)

    @Query("SELECT * FROM path_table")
    fun getAll(): Flow<List<PathData>>

    @Query("SELECT * FROM path_table WHERE id = :id")
    fun getPathById(id: Long): Flow<PathData>

    @Update
    suspend fun updatePath(path: PathData)
}