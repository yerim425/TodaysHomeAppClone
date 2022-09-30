package com.example.risingtest.src.main.store.productDetail.models.productDetail

import java.util.*

data class Review(
    val reviewId: Long,
    val productId: Long,
    val writerIdx: Long,
    val writerName: String,
    val productName: String,
    val productPhotoUrl: String,
    val content: String,
    val reviewPhotoUrl: String,
    val score: Int,
    val durability: Int,
    val design: Int,
    val price: Int,
    val delivery: Int,
)
