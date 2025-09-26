package com.tlt.task.data.api

import com.tlt.task.data.model.Product

data class ApiResponse(
    val success: Boolean,
    val data: List<Product>
)
