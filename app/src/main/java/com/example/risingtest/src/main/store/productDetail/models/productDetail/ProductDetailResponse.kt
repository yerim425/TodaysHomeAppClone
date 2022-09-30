package com.example.risingtest.src.main.store.productDetail.models.productDetail

data class ProductDetailResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ResultProductDetail
)