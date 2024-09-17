package com.mizani.labis.data.remote.store

import com.mizani.labis.data.remote.SuccessResponse
import com.mizani.labis.data.request.store.StoreCreateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface StoreService {

    @POST("/api/auth/store/create")
    suspend fun createStore(
        @Body request: StoreCreateRequest
    ): SuccessResponse<Any>


    @GET("/api/auth/stores")
    suspend fun getStores(): StoreResponse

    @GET("/api/auth/store")
    suspend fun getStore(
        @Query("id") id: Int
    ): StoreDataResponse

    @PATCH("/api/auth/store/update")
    suspend fun updateStore(
        @Body request: StoreCreateRequest
    ): SuccessResponse<Any>

}