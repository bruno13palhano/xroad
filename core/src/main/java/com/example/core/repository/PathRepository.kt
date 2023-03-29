package com.example.core.repository

import com.example.model.model.Difficulty
import com.example.model.model.Path
import kotlinx.coroutines.flow.Flow

interface PathRepository {
    suspend fun insert(path: Path)
    fun getAll(): Flow<List<Path>>
    fun getPathById(id: Long): Flow<Path>
    suspend fun updatePath(path: Path)
    suspend fun updatePathTitle(title: String, id: Long)
    suspend fun updatePathTopic(topic: String, id: Long)
    suspend fun updatePathDescription(description: String, id: Long)
    suspend fun updatePathDuration(duration: Long, id: Long)
    suspend fun updatePathDate(date: Long, id: Long)
    suspend fun updatePathDifficulty(difficulty: Difficulty, id: Long)
}