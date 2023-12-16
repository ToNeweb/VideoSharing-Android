package com.aghajari.videosharing.screen.login.viewmodel

import android.util.Patterns
import com.aghajari.videosharing.model.ScreenState
import com.aghajari.videosharing.model.ScreenViewModel
import kotlinx.coroutines.delay

class LoginViewModel : ScreenViewModel() {

    var emailAddress: String? = null
        private set

    var username: String? = null
        private set

    fun updateEmailAddress(email: String?) {
        launchIO {
            updateState(ScreenState.LOADING)
            delay(1000)
            if (!email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailAddress = email
                updateState(ScreenState.SUCCESS)
            } else {
                updateState(ScreenState.ERROR)
            }
        }
    }

    suspend fun updateUsername(username: String?): Boolean {
        this.username = username
        return true
    }

    fun validateCode(code: String) {
        launchIO {
            updateState(ScreenState.LOADING)
            delay(500)
            if (code == "12345") {
                updateState(ScreenState.SUCCESS)
            } else {
                updateState(ScreenState.ERROR)
            }
        }
    }

    fun notifySwitchScreen() {
        updateState(ScreenState.EMPTY)
    }
}