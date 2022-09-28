package com.example.risingtest.src.main.store.models.productScrap

import com.google.gson.annotations.SerializedName

data class PostProductScrapRequest(
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("productId") val productId: Int,
)