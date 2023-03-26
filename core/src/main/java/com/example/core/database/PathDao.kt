package com.example.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PathDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(path: PathData)

    @Query("SELECT * FROM path_table")
    fun getAll(): Flow<List<PathData>>
}