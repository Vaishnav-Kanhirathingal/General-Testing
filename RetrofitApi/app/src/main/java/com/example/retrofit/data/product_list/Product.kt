package com.example.retrofit.data.product_list

data class Product(
    val brand: String,
    val category: Category,
    val id: Int,
    val name: String,
    val price: String
)