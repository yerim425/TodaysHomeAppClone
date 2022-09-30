package com.example.risingtest.src.scrap.fragments.scrapAll

import com.example.risingtest.src.scrap.fragments.scrapAll.scrapedAllItem.ScrapedAllItemResponse

interface ScrapedAllFragmentInterface {

    fun onGetScrapedAllItemSuccess(response: ScrapedAllItemResponse)
    fun onGetScrapedAllItemFailure(message: String)
}