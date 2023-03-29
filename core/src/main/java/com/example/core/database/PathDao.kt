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

    @Query("UPDATE path_table SET title = :title WHERE id = :id")
    suspend fun updatePathTitle(title: String, id: Long)

    @Query("UPDATE path_table SET topic = :topic WHERE id = :id")
    suspend fun updatePathTopic(topic: String, id: Long)

    @Query("UPDATE path_table SET description = :description WHERE id = :id")
    suspend fun updatePathDescription(description: String, id: Long)

    @Query("UPDATE path_table SET duration = :duration WHERE id = :id")
    suspend fun updatePathDuration(duration: Long, id: Long)

    @Query("UPDATE path_table SET date = :date WHERE id = :id")
    suspend fun updatePathDate(date: Long, id: Long)

    @Query("UPDATE path_table SET difficulty = :difficulty WHERE id = :id")
    suspend fun updatePathDifficulty(difficulty: Difficulty, id: Long)
}