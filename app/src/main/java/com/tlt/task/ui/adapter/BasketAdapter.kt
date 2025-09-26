package com.tlt.task.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tlt.task.R
import com.tlt.task.data.model.Product

class BasketAdapter : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    private var basketItems = listOf<Product>()

    fun updateItems(newItems: List<Product>) {
        basketItems = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_basket, parent, false)
        return BasketViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(basketItems[position])
    }

    override fun getItemCount() = basketItems.size

    inner class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvBasketItemName)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvBasketItemPrice)

        fun bind(product: Product) {
            tvName.text = product.product_name
            val price = product.cost_price ?: product.retail_price
            tvPrice.text = "€${String.format("%.2f", price)}"
        }
    }
}