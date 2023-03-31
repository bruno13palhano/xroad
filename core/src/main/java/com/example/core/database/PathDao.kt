package com.example.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PathDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(path: PathData)

    @Query("SELECT * FROM path_table")
    fun getAllStream(): Flow<List<PathData>>

    @Query("SELECT * FROM path_table WHERE id = :id")
    fun getPathByIdStream(id: Long): Flow<PathData>

    @Update
    suspend fun updatePath(path: PathData)

    @Query("SELECT COUNT(id) FROM path_table")
    fun getPathCountStream(): Flow<Int>
}