package com.example.risingtest.src.main.store.models.storePage

import java.util.*

data class StorePageProduct(
    val brandName: String,
    val discountedPrice: Int,
    val isScrabbed: Boolean,
    val name: String,
    val numReviews: Int,
    val originalPrice: Int,
    val productId: Int,
    val thumbnailUrl: String,
    val totalScore: Float,
    var isTodaysDeal: Boolean,
    var eventDeadline: String,
    var remainDate: String ?= null,
)
