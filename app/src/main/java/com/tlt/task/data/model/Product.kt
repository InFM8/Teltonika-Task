package com.tlt.task.data.model

import java.io.Serializable

data class Product(
    val product_name: String,
    val cost_price: Double?,
    val retail_price: Double,
    val currency: String
) : Serializable
