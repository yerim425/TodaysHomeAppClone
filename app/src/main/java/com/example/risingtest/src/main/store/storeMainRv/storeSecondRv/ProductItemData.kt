package com.example.risingtest.src.main.store.storeMainRv.storeSecondRv

data class ProductItemData(
    val title: String ?= null,
    val salePercent: String ?= null,
    val Price: String ?= null,
    val userRating: String?= null,
    val reviewNum: String?= null,
    val remainDate: String?= null,
    val isForeignProduct: Boolean?= null,
    val isFreeDelivery: Boolean?= null,
    val isSpecialPrice: Boolean?= null
)
