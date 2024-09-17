package com.mizani.labis.domain.repository

import com.mizani.labis.domain.model.dto.ErrorDto
import com.mizani.labis.domain.model.dto.Result

interface AuthRepository {

    suspend fun getTokenClient(): String

    suspend fun getTokenUser(email: String, password: String): Result<String, ErrorDto>

}