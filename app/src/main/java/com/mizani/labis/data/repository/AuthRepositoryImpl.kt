package com.mizani.labis.data.repository

import com.mizani.labis.data.remote.auth.AuthService
import com.mizani.labis.data.request.auth.LoginRequest
import com.mizani.labis.domain.model.dto.ErrorDto
import com.mizani.labis.domain.model.dto.Result
import com.mizani.labis.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun getTokenClient(): String {
        return authService.getTokenClient().accessToken
    }

    override suspend fun getTokenUser(email: String, password: String): Result<String, ErrorDto> {
        try {
            val data = authService.getTokenUser(LoginRequest(email, password))
            if (data.isSuccessful) {
                return Result.Success(data.body()?.accessToken.orEmpty())
            } else {
                return Result.Error(ErrorDto(data.errorBody()?.toString().orEmpty()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(ErrorDto(e.message.toString()))
        }
    }
}