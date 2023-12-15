package com.aghajari.videosharing.screen.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aghajari.videosharing.screen.login.model.ValidationStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var emailAddress: String? = null
        private set

    var username: String? = null
        private set

    private var validationJob: Job? = null

    private val _validationStatus = MutableStateFlow(ValidationStatus.NO_RES)
    val validationStatus: StateFlow<ValidationStatus> = _validationStatus

    suspend fun updateEmailAddress(email: String?): Boolean {
        //if (!email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        emailAddress = email
        return true
        //}
        //return false
    }

    suspend fun updateUsername(username: String?): Boolean {
        this.username = username
        return true
    }

    fun validateCode(code: String) {
        cancelValidation()
        validationJob = viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            if (code == "12345") {
                _validationStatus.value = ValidationStatus.SUCCESS
            } else {
                _validationStatus.value = ValidationStatus.ERROR
            }
        }
    }

    fun cancelValidation() {
        validationJob?.cancel()
        validationJob = null
    }

    fun clearValidation() {
        _validationStatus.value = ValidationStatus.NO_RES
    }
}