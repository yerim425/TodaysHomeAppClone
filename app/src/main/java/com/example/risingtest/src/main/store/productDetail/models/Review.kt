package com.example.risingtest.src.main.store.productDetail.models

import java.util.*

data class Review(
    val reviewId: Long,
    val writerIdx: Long,
    val nickname: String,
    val score: Float,
    val reviewPhotos: List<String>,
    val text: String,
    val createdAt: Date
)
