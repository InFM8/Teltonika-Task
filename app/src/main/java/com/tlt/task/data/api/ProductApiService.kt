package com.tlt.task.data.api

import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): ApiResponse
}