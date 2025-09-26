package com.tlt.task.ui.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tlt.task.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor() : ViewModel() {

    private val _basketItems = MutableLiveData<List<Product>>(emptyList())
    val basketItems: LiveData<List<Product>> = _basketItems

    private val _totalPrice = MutableLiveData<Double>(0.0)
    val totalPrice: LiveData<Double> = _totalPrice

    fun addProduct(product: Product) {
        val currentItems = _basketItems.value?.toMutableList() ?: mutableListOf()
        currentItems.add(product)
        _basketItems.value = currentItems
        calculateTotal()
    }

    fun clearBasket() {
        _basketItems.value = emptyList()
        _totalPrice.value = 0.0
    }

    private fun calculateTotal() {
        val total = _basketItems.value?.sumOf { product ->
            product.cost_price ?: product.retail_price
        } ?: 0.0
        _totalPrice.value = total
    }
}