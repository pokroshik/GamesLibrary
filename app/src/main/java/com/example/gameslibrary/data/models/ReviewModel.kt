package com.example.gameslibrary.data.models

data class ReviewModel (
    val gameTitle: String = "",
    val user: String = "",
    val uid: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val timestamp: Long = System.currentTimeMillis()
)