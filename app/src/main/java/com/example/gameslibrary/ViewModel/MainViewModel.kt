package com.example.gameslibrary.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gameslibrary.Domain.GameModel
import com.example.gameslibrary.Repository.MainRepository

class MainViewModel:ViewModel() {
    private val repository = MainRepository()

    fun loadUpcoming():LiveData<MutableList<GameModel>> {
        return repository.loadUpcoming()
    }
}