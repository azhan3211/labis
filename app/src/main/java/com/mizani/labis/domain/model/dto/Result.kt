package com.mizani.labis.domain.model.dto

sealed class Result<out Success, out Error>() {
    data class Success<out Success>(val data: Success) : Result<Success, Nothing>()
    data class Error<out Error>(val error: Error) : Result<Nothing, Error>()
}