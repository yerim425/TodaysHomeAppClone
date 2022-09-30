package com.example.risingtest.src.main.store.productDetail.models.productDetail

data class Category(
    val categoryId: Int,
    val categoryName: String,
    val createdAt: Any,
    val depth: Int,
    val parentCategoryId: Int,
    val status: String,
    val updatedAt: String
)