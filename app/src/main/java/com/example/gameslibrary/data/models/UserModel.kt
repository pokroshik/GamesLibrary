package com.example.gameslibrary.data.models

import com.google.firebase.Timestamp
import java.io.Serializable

data class UserModel (
    val createdAt: Timestamp? = null,
    val lastVisited: Timestamp? = null,
    val birthday: Timestamp? = null,
    val login: String? = "",
    val firstName :String? = null,
    val lastName: String? = null,
    val bio: String? = null,
    val country: String? = null,
    val gender: String? = null,
    val steamProfile :String? = null
):Serializable