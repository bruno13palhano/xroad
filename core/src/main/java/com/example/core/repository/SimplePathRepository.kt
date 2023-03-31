package com.example.core.repository

import com.example.core.database.PathDao
import com.example.core.database.asPath
import com.example.core.database.asPathData
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

    override fun getAllStream(): Flow<List<Path>> {
        return pathDao.getAllStream().map {
            it.map { pathData ->
                pathData.asPath()
            }
        }
    }

    override fun getPathByIdStream(id: Long): Flow<Path> {
        return pathDao.getPathByIdStream(id).map {
            it.asPath()
        }
    }

    override suspend fun updatePath(path: Path) {
        pathDao.updatePath(path.asPathData())
    }

    override fun getPathCountStream(): Flow<Int> {
        return pathDao.getPathCountStream()
    }
}