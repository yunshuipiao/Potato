package com.swensun.func.kotlin

import androidx.lifecycle.ViewModel
import com.swensun.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class KotlinViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(0)
    val uiState: StateFlow<Int> = _uiState
    private var updateUIJob: Job? = null

    fun updateUI() {
        updateUIJob?.cancel()
        updateUIJob = launch {
            while (true) {
                delay(1000)
                var value = uiState.value
                value += 1
                _uiState.value = value

            }
        }
    }
}