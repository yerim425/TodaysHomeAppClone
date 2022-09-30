package com.example.risingtest.src.scrap.fragments.scrapAll.scrapedAllItem

data class ScrapedAllItemResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ResultScrapedAllItem
)