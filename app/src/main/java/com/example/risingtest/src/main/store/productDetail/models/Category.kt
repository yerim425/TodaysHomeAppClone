package com.example.risingtest.src.main.store.productDetail.models

import java.util.*

data class Category(
    val categoryId: Int,
    val parentCategoryId: Int,
    val categoryName: String,
    val depth: Int,
    val createdAt: Date,
    val updateAt: Date,
    val status: String,
)
