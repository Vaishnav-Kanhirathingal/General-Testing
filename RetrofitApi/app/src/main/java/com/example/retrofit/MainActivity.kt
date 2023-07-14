package com.example.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.adapters.BrandAdapter
import com.example.retrofit.adapters.ProductAdapter
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.view_model.RetroViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel = RetroViewModel()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyBinding()
    }

    private fun applyBinding() {
        val brandAdapter = BrandAdapter()
        viewModel.getBrandsList { brandAdapter.submitList(it) }

        val productAdapter = ProductAdapter()
        viewModel.getProductList { productAdapter.submitList(it) }

        binding.recyclerView.adapter = brandAdapter

        binding.brandSelector.setOnClickListener { binding.recyclerView.adapter = brandAdapter }
        binding.productSelector.setOnClickListener { binding.recyclerView.adapter = productAdapter }
    }
}