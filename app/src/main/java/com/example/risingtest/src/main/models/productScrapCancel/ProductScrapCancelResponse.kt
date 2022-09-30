package com.example.risingtest.src.main.models.productScrapCancel

import com.google.gson.annotations.SerializedName

data class ProductScrapCancelResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null
)
