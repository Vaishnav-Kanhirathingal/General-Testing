package com.example.retrofit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.data.brands_list.Brand
import com.example.retrofit.databinding.BrandViewBinding

class BrandAdapter : ListAdapter<Brand, BrandAdapter.BrandViewHolder>(diffCallBack) {
    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Brand>() {
            override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean =
                oldItem.brand == newItem.brand
        }
    }

    class BrandViewHolder(private val binding: BrandViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(brand: Brand) {
            binding.itemId.text = brand.id.toString()
            binding.brandName.text = brand.brand
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder(
            BrandViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}