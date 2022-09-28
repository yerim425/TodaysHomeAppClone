package com.example.risingtest.src.main.store.models.storePage

data class StorePageProduct(
    val brandName: String,
    val discountedPrice: Int,
    val isScrabbed: Boolean,
    val name: String,
    val numReviews: Int,
    val originalPrice: Int,
    val productId: Int,
    val thumbnailUrl: String,
    val totalScore: Double,
    var isTodaysDeal: Boolean ?= false
)
