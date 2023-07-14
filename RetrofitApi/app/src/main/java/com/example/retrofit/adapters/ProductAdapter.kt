package com.example.retrofit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.data.product_list.Product
import com.example.retrofit.databinding.ProductViewBinding

class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(diffCallBack) {
    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                newItem.id == oldItem.id


            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.brand == newItem.brand &&
                        oldItem.name == newItem.name &&
                        oldItem.price == newItem.price &&
                        oldItem.category == newItem.category
            }
        }
    }

    class ProductViewHolder(private val binding: ProductViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            // TODO: bind
            binding.productId.text = product.id.toString()
            binding.name.text = product.name
            binding.brand.text = product.brand
            binding.category.text = "(${product.category.category})"
            binding.userType.text = "(${product.category.usertype.usertype})"
            binding.price.text = product.price


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}