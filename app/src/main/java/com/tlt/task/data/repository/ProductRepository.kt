package com.tlt.task.data.repository

import com.tlt.task.data.api.ProductApiService
import com.tlt.task.data.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val apiService: ProductApiService
) {
    suspend fun getProducts(): List<Product> {
        return apiService.getProducts().data
    }
}