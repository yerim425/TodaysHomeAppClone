package com.example.risingtest.src.main.store.models.storePage

data class ResultStorePage(
    val advertises: List<String>,
    val favorites: List<StorePageProduct>,
    val todaysDeal: List<StorePageProduct>
)
