package com.example.risingtest.src.scrap.fragments.scrapProduct.scrapedProductItem

data class ScrapedProductItemResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ResultScrapedProductItem
)