package com.example.model.model

data class Path (
    val id: Long,
    val title: String,
    val description: String,
    val topic: String,
    val difficulty: Difficulty,
    val durationInMilliseconds: Long,
    val dateInMilliseconds: Long
)