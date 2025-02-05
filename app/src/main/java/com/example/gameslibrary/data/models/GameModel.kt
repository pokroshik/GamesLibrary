package com.example.gameslibrary.data.models

import com.google.firebase.Timestamp
import java.io.Serializable

data class GameModel(
    var Developer:String="",
    var Genre:ArrayList<String> = ArrayList(),
    var Metacritic:MutableMap<String, Double?> = mutableMapOf(),
    var Title:String="",
    var about:String="",
    var controller: Boolean? = true,
    var disk: String? = "",
    var platforms:ArrayList<String> = ArrayList(),
    var poster:String="",
    var release: Timestamp? = null,
    var require: MutableMap<String, Any> = mutableMapOf(),
    var stores: MutableMap<String, String> = mutableMapOf(),
    var time: MutableMap<String, Double?> = mutableMapOf()
):Serializable /*{

    // Пустой конструктор для Firebase
    constructor() : this(
        Developer = "",
        Genre = ArrayList(),
        Metacritic = ArrayList(),
        Title = "",
        about = "",
        controller = true,
        disk = "",
        platforms = ArrayList(),
        poster = "",
        release = 0L,
        require = mutableMapOf(),
        stores = mutableMapOf(),
        time = mutableMapOf()
    )
}*/
