package com.mizani.labis.data.request.auth

import com.google.gson.annotations.SerializedName
import com.mizani.labis.BuildConfig

data class LoginRequest(
    val email: String,
    val password: String,
    @SerializedName("client_id")
    val clientId: String = BuildConfig.CLIENT_ID,
    @SerializedName("client_secret")
    val clientSecret: String = BuildConfig.CLIENT_SECRET,
)