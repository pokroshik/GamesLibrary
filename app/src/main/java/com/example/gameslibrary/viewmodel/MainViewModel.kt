package com.example.gameslibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gameslibrary.data.models.GameModel
import com.example.gameslibrary.repository.MainRepository

class MainViewModel:ViewModel() {
    private val repository = MainRepository()

    fun loadUpcoming():LiveData<MutableList<GameModel>> {
        return repository.loadUpcoming()
    }
}