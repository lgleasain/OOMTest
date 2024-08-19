package com.example.oomtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<TestState>(TestState())
    val uiState: StateFlow<TestState> = _uiState

    init {
        viewModelScope.launch {
            while(true) {
                delay(1000)
                _uiState.value = uiState.value.copy(animate = true, number = uiState.value.number + 1)
                println("state updated")
            }
        }
    }

    fun resetAnimation() {
        _uiState.value = uiState.value.copy(animate = false)
        println("state reset")
    }

    override fun onCleared() {
        super.onCleared()
        println("viewmodel cleared")
    }
}

data class TestState(
    val animate: Boolean = false,
    val number: Int = 0
)