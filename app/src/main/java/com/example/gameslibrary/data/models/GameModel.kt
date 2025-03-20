package com.example.gameslibrary.data.models

import com.google.firebase.Timestamp
import java.io.Serializable

data class GameModel(
    val Developer:String="",
    val Genre:ArrayList<String> = ArrayList(),
    val Metacritic:MutableMap<String, Double?> = mutableMapOf(),
    val Title:String="",
    val controller: Boolean? = true,
    val disk: String? = "",
    val platforms:ArrayList<String> = ArrayList(),
    val poster:String="",
    val release: Timestamp? = null,
    val about:String="",
    val require: MutableMap<String, Any> = mutableMapOf(),
    val stores: MutableMap<String, String> = mutableMapOf(),
    val time: MutableMap<String, Double?> = mutableMapOf(),
    val images: ArrayList<String> = ArrayList(),
    var documentId: String = "",
    var isWish: Boolean = false,
    var isPlayed: Boolean = false,
):Serializable
