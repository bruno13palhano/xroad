package com.example.core.repository

import com.example.model.model.Path
import kotlinx.coroutines.flow.Flow

interface PathRepository {
    suspend fun insert(path: Path)
    fun getAllStream(): Flow<List<Path>>
    fun getPathByIdStream(id: Long): Flow<Path>
    suspend fun updatePath(path: Path)
    fun getPathCountStream(): Flow<Int>
}