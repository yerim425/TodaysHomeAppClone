package com.example.risingtest.src.main.store.productDetail.models.productDetail

data class ResultProductDetail(
    val brandName: String,
    val category2: Int,
    val categoryList: List<Category>,
    val discountedPrice: Int,
    val expPhotos: List<String>,
    val numReviews: Int,
    val options: List<Option>,
    val price: Int,
    val productId: Int,
    val productName: String,
    val productPhotos: List<String>,
    val reviews: List<Review>,
    val totalScore: Float
)