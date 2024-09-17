package com.mizani.labis.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.labis.domain.repository.AuthRepository
import com.mizani.labis.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    fun requestToken() {
        viewModelScope.launch {
            val token = authRepository.getTokenClient()
            preferenceRepository.setToken(token)
        }
    }

    fun getToken(): String {
        return preferenceRepository.getToken()
    }

    fun isLogin(): Boolean {
        return preferenceRepository.isLogin()
    }

}