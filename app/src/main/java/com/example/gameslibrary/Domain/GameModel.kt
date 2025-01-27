package com.example.gameslibrary.Domain

import java.io.Serializable

data class GameModel(
    var Title:String="",
    var Description:String="",
    var ReleaseDate:String="",
    var Trailer:String="",
    var Metacritic:String="",
    var Price:Double=0.0,
    var OS:ArrayList<String> = ArrayList(),
    var Genre:ArrayList<String> = ArrayList(),

):Serializable
