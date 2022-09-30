package com.example.risingtest.src.scrap.fragments.scrapProduct.scrapedProductItem

data class ScrabItem(
    val brandName: String,
    val discountedPrice: Int,
    val numReviews: Int,
    val originalPrice: Int,
    val productId: Int,
    val productName: String,
    val thumbnailUrl: String,
    val totalScore: Int
)