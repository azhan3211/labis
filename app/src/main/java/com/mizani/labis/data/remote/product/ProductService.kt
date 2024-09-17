package com.mizani.labis.data.remote.product

import com.mizani.labis.data.remote.SuccessResponse
import com.mizani.labis.data.request.product.ProductCreateRequest
import com.mizani.labis.data.request.product.ProductTypeCreateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @POST("/api/auth/product/create")
    suspend fun createProduct(
        @Body request: ProductCreateRequest
    ): Response<SuccessResponse<Any>>

    @GET("/api/auth/product")
    suspend fun getProduct(
        @Query("id") productId: Int
    ): Response<SuccessResponse<ProductResponse>>

    @GET("/api/auth/products")
    suspend fun getProducts(
        @Query("store_id") storeId: Int
    ): Response<SuccessResponse<List<ProductResponse>>>

    @POST("/api/auth/product-type/create")
    suspend fun createProductType(
        @Body request: ProductTypeCreateRequest
    ): Response<SuccessResponse<Any>>

    @GET("/api/auth/product-types")
    suspend fun getProductTypes(
        @Query("store_id") storeId: Int
    ): Response<SuccessResponse<List<ProductTypeResponse>>>

    @GET("/api/auth/product-type")
    suspend fun getProductType(id: Int): Response<SuccessResponse<Any>>

    @POST("/api/auth/product/photo")
    suspend fun uploadPhoto(): Response<SuccessResponse<Any>>

    @PATCH("/api/auth/product/update")
    suspend fun updateProduct(
        @Body request: HashMap<String, String>
    ): Response<SuccessResponse<Any>>
}