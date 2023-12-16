package com.aghajari.videosharing.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class ScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(ScreenState.EMPTY)
    val state: StateFlow<ScreenState> = _state

    private var job: Job? = null

    protected fun updateState(state: ScreenState) {
        _state.value = state
    }

    fun launchIO(block: suspend CoroutineScope.() -> Unit) {
        cancelJob()
        job = viewModelScope.launch(Dispatchers.IO, block = block)
    }

    fun cancelJob() {
        job?.cancel()
        job = null
    }
}