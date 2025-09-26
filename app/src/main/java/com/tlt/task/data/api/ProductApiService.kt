package com.tlt.task.data.api

import retrofit2.http.GET

interface ProductApiService {
    @GET("v1/83e100f1-f50e-4d64-88a0-96424011f32d")
    suspend fun getProducts(): ApiResponse
}