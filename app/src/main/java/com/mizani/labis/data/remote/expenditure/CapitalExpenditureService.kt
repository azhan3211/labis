package com.mizani.labis.data.remote.expenditure

import com.mizani.labis.data.remote.SuccessResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface CapitalExpenditureService {

    @GET("/api/auth/capital/expenditures")
    suspend fun getCapitalExpenditure(
        @QueryMap request: HashMap<String, String>
    ): Response<SuccessResponse<CapitalExpenditureResponse>>

    @FormUrlEncoded
    @POST("/api/auth/capital/expenditure")
    suspend fun createCapitalExpenditure(
        @FieldMap request: HashMap<String, String>
    ): Response<SuccessResponse<Any>>

    @PATCH("/api/auth/capital/expenditure/update")
    suspend fun updateCapitalExpenditure(): Response<SuccessResponse<Any>>

    @FormUrlEncoded
    @POST("/api/auth/capital/expenditure/delete")
    suspend fun deleteCapitalExpenditure(
        @Field("store_id") storeId: Int,
        @Field("id") id: Int,
    ): Response<SuccessResponse<Any>>


}