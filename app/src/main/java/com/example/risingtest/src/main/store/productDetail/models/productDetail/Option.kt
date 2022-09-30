package com.example.risingtest.src.main.store.productDetail.models.productDetail

data class Option(
    val createdAt: String,
    val optionName: String,
    val optionPrice: Int,
    val productId: Int,
    val productOptionId: Int,
    val status: String,
    val updatedAt: String
)