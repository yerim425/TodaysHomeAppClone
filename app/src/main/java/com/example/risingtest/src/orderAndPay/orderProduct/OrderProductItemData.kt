package com.example.risingtest.src.orderAndPay.orderProduct

data class OrderProductItemData(

    val brandName: String,
    val imageUrl: String,
    val productName: String,
    val optionName: String,
    val productId: Int,
    val optionId: Int,
    val price: Int,
    val productNum: Int
)
