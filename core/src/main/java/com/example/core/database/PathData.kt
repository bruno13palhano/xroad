package com.example.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.model.Path

@Entity(tableName = "path_table")
internal data class PathData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "duration")
    val durationInMilliseconds: Long,

    @ColumnInfo(name = "date")
    val dateInMilliseconds: Long
)

internal fun PathData.asPath() = Path(
    id = id,
    title = title,
    description = description,
    durationInMilliseconds = durationInMilliseconds,
    dateInMilliseconds = dateInMilliseconds
)

internal fun Path.asPathData() = PathData(
    id = id,
    title = title,
    description = description,
    durationInMilliseconds = durationInMilliseconds,
    dateInMilliseconds = dateInMilliseconds
)