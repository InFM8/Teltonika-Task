package com.tlt.task

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tlt.task.ui.adapter.ProductAdapter
import com.tlt.task.ui.productlist.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {
    private var TAG = "ProductListActivity"

    private val productListViewModel: ProductListViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private lateinit var btnRefresh: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        initViews()
        setupRecyclerView()
        observeViewModel()

        productListViewModel.loadProducts()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerViewProducts)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)
        btnRefresh = findViewById(R.id.btnRefresh)

        btnRefresh.setOnClickListener {
            Log.d(TAG, "On refresh button click")
            productListViewModel.loadProducts()
        }
    }

    private fun setupRecyclerView() {
        val adapter = ProductAdapter { product ->


            setResult(RESULT_OK, Intent().putExtra("selected_product", product))
            finish()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        productListViewModel.products.observe(this) { products ->
            (recyclerView.adapter as ProductAdapter).updateProducts(products)
        }

        productListViewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        productListViewModel.error.observe(this) { error ->
            tvError.text = error ?: ""
            tvError.visibility = if (error != null) View.VISIBLE else View.GONE
        }
    }
}