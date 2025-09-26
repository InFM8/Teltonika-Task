package com.tlt.task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tlt.task.data.model.Product
import com.tlt.task.ui.adapter.BasketAdapter
import com.tlt.task.ui.basket.BasketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val basketViewModel: BasketViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var btnOpenProducts: Button
    private lateinit var btnClearBasket: Button
    private lateinit var tvEmptyBasket: TextView

    private val productListLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d(TAG, "Result code: ${result.resultCode}")
        if (result.resultCode == RESULT_OK) {
            val product = result.data?.getSerializableExtra("selected_product") as? Product
            Log.d(TAG, "Product received: $product")
            product?.let {
                basketViewModel.addProduct(it)
                Log.d(TAG, "Product added to basket")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        observeViewModel()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerViewBasket)
        tvTotal = findViewById(R.id.tvTotal)
        btnOpenProducts = findViewById(R.id.btnOpenProducts)
        btnClearBasket = findViewById(R.id.btnClearBasket)
        tvEmptyBasket = findViewById(R.id.tvEmptyBasket)

        btnOpenProducts.setOnClickListener {
            Log.d(TAG, "On Open Product Btn Click")
            productListLauncher.launch(Intent(this, ProductListActivity::class.java))
        }

        btnClearBasket.setOnClickListener {
            Log.d(TAG, "On Clear Product Btn Click")
            basketViewModel.clearBasket()
        }
    }

    private fun setupRecyclerView() {
        val adapter = BasketAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        basketViewModel.basketItems.observe(this) { items ->
            (recyclerView.adapter as BasketAdapter).updateItems(items)
            tvEmptyBasket.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        basketViewModel.totalPrice.observe(this) { total ->
            tvTotal.text = "Total: €${String.format("%.2f", total)}"
        }
    }
}