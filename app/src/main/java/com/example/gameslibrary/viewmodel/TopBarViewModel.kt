package com.example.gameslibrary.viewmodel

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor() : ViewModel() {
    var uid = mutableStateOf("123")
    var mainUid = mutableStateOf("123")
    var title = mutableStateOf("")
    var subtitle = mutableStateOf("")

    private var _tab = MutableStateFlow(0)
    var tab: StateFlow<Int> = _tab.asStateFlow()

    private var _isEdited = MutableStateFlow(false)
    var isEdited: StateFlow<Boolean> = _isEdited.asStateFlow()

    fun updateTitle(newTitle: String, newSubtitle: String) {
        title.value = newTitle
        subtitle.value = newSubtitle
    }

    fun changeProfile() {
        viewModelScope.launch {
            _isEdited.value = !_isEdited.value
        }
    }

    fun changeUid(uid1: String) {
        if (uid1 == "0") {
            uid.value = mainUid.value
        } else
            uid.value = uid1
    }

    fun setSelectedTab(index: Int) {
        _tab.value = index
    }

    fun setUser(uid: String) {
        mainUid.value = uid
    }
}