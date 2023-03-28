package com.example.core.repository

import com.example.model.model.Path
import kotlinx.coroutines.flow.Flow

interface PathRepository {
    suspend fun insert(path: Path)
    fun getAll(): Flow<List<Path>>
    fun getPathById(id: Long): Flow<Path>
}