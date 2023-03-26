package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PathData::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class XRoadDatabase : RoomDatabase() {
    abstract val pathDao: PathDao
}