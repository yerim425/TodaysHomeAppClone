package com.example.risingtest.src.scrap

import com.example.risingtest.src.scrap.fragments.scrapAll.scrapedAllItem.ScrapedAllItemResponse
import com.example.risingtest.src.scrap.fragments.scrapProduct.scrapedProductItem.ScrapedProductItemResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ScrapRetrofitInterface {

    @GET("/api/users/{userIdx}/scrabs")
    fun getScrapedAllItem(
        @Path("userIdx") userIdx: Int
    ): Call<ScrapedAllItemResponse>

    @GET("/api/users/{userIdx}/scrabs/product")
    fun getScrapedProductItem(
        @Path("userIdx") userIdx: Int,
    ): Call<ScrapedProductItemResponse>
}