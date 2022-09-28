package com.example.risingtest.src.main.store.productDetail.models

data class ResultProductDetail(
    val categoryList: List<Category>,
    val productId: Int,
    val productName: String,
    val productPhotos: List<String>,
    val totalScore: Double,
    val numReviews: Int,
    val price: Int,
    val discountedPrice: Int,
    val brandName: String,
    val expPhotos: List<String>,
    val reviews: List<Review>,
    val options: List<Option>,

)