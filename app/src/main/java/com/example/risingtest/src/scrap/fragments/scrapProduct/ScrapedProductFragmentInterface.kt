package com.example.risingtest.src.scrap.fragments.scrapProduct

import com.example.risingtest.src.scrap.fragments.scrapProduct.scrapedProductItem.ScrapedProductItemResponse

interface ScrapedProductFragmentInterface {

    fun onGetScrapedProductItemSuccess(response: ScrapedProductItemResponse)
    fun onGetScrapedProductItemFailure(message: String)


}