package com.example.risingtest.src.orderAndPay.paymentMeans

data class PaymentMeansItemData(
    val title: String,
    val Image: Int,
    val benefit: String ?= null,
    val benefitDetail: Int ?= null,
)
