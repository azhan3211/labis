package com.mizani.labis.ui.screen.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.labis.domain.model.dto.Result
import com.mizani.labis.domain.repository.AuthRepository
import com.mizani.labis.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    val isLoginSuccess: State<Boolean> get() = _isLoginSuccess
    private val _isLoginSuccess = mutableStateOf(false)

    val loginError: State<String> get() = _loginError
    private val _loginError = mutableStateOf("")

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginError.value = ""
            when (val result = authRepository.getTokenUser(email, password)) {
                is Result.Success -> {
                    preferenceRepository.setToken(result.data)
                    preferenceRepository.setLogin(true)
                    _isLoginSuccess.value = true
                }
                is Result.Error -> {
                    _loginError.value = result.error.message
                    _isLoginSuccess.value = false
                }
            }
        }
    }

}