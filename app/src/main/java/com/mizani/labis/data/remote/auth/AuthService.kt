package com.mizani.labis.data.remote.auth

import com.mizani.labis.BuildConfig
import com.mizani.labis.data.request.auth.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @POST("/api/auth/owneremployee/login")
    suspend fun getTokenUser(
        @Body request: LoginRequest
    ): Response<TokenResponse>

    @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun getTokenClient(
        @Field("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Field("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Field("grant_type") grantType: String = "client_credentials",
    ): TokenResponse

}