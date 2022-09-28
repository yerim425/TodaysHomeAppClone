package com.example.risingtest.src.main.store.storeMainRv.storeProductRv

data class StoreProductItemData(
    val title: String ?= null,
    val img: Int ?= null,
    val brand: String ?= null,

    val salePercent: String ?= null,
    val Price: String ?= null,

    val userRating: String?= null,
    val reviewNum: String?= null,

    val remainDate: String?= null,
    val isChecked: Boolean ?= null,

    val isForeignProduct: Boolean?= null,
    val isFreeDelivery: Boolean?= null,
    val isSpecialPrice: Boolean?= null,


    val productId: Int,
    val name: String,
    val thumbnailUrl: String,
    val brandName: String,

    val discountedPrice: Int,
    val originalPrice: Int,

    val totalScore: Double,
    val numReviews: Int,

    val isScrabbed: Any,


)
