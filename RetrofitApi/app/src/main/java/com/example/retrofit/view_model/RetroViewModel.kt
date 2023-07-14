package com.example.retrofit.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.api.retroService
import com.example.retrofit.data.brands_list.Brand
import com.example.retrofit.data.product_list.Product
import kotlinx.coroutines.launch

class RetroViewModel : ViewModel() {
    fun getProductList(setList: (List<Product>) -> Unit) {
        viewModelScope.launch {
            val x = retroService.getProductList()
            setList(
                if (x.responseCode == 200) {
                    x.products
                } else {
                    mutableListOf()
                }
            )
        }
    }

    fun getBrandsList(setList: (List<Brand>) -> Unit) {
        viewModelScope.launch {
            val x = retroService.getBrandsList()
            setList(
                if (x.responseCode == 200) {
                    x.brands
                } else {
                    mutableListOf()
                }
            )
        }
    }
}