package com.example.core.repository

import com.example.core.database.PathDao
import com.example.core.database.asPath
import com.example.core.database.asPathData
import com.example.model.model.Difficulty
import com.example.model.model.Path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SimplePathRepository @Inject constructor(
    private val pathDao: PathDao
) : PathRepository {
    override suspend fun insert(path: Path) {
        pathDao.insert(path.asPathData())
    }

    override fun getAll(): Flow<List<Path>> {
        return pathDao.getAll().map {
            it.map { pathData ->
                pathData.asPath()
            }
        }
    }

    override fun getPathById(id: Long): Flow<Path> {
        return pathDao.getPathById(id).map {
            it.asPath()
        }
    }

    override suspend fun updatePath(path: Path) {
        pathDao.updatePath(path.asPathData())
    }

    override suspend fun updatePathTitle(title: String, id: Long) {
        pathDao.updatePathTitle(title, id)
    }

    override suspend fun updatePathTopic(topic: String, id: Long) {
        pathDao.updatePathTopic(topic, id)
    }

    override suspend fun updatePathDescription(description: String, id: Long) {
        pathDao.updatePathDescription(description, id)
    }

    override suspend fun updatePathDuration(duration: Long, id: Long) {
        pathDao.updatePathDuration(duration, id)
    }

    override suspend fun updatePathDate(date: Long, id: Long) {
        pathDao.updatePathDate(date, id)
    }

    override suspend fun updatePathDifficulty(difficulty: Difficulty, id: Long) {
        pathDao.updatePathDifficulty(difficulty, id)
    }
}