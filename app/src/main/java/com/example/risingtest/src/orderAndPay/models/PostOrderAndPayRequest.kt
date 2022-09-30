package com.example.risingtest.src.orderAndPay.models

data class PostOrderAndPayRequest(
    val address1: String,
    val address2: String,
    val addressName: String,
    val buyerIdx: Int,
    val buyerName: String,
    val email: String,
    val paymentMethod: String,
    val phoneNumber: String,
    val postalCode: String,
    val price: Int,
    val productId: Int,
    val productOptionId: Int,
    val receiverName: String,
    val receiverPhoneNumber: String,
    val request: String,
    val selectAsDefault: Boolean
)