package com.example.risingtest.src.main.store.models.storePage

data class StorePageResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ResultStorePage
)