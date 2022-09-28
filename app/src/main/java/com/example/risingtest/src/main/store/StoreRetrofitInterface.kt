package com.example.risingtest.src.main.store

import com.example.risingtest.src.main.store.productDetail.models.ProductDetailResponse
import com.example.risingtest.src.main.store.models.productScrap.ProductScrapResponse
import com.example.risingtest.src.main.store.models.storePage.StorePageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreRetrofitInterface {

    // 스토어 화면
   @GET("/api/products")
    fun getMyProfile(@Query("userIdx") userIdx: Int) : Call<StorePageResponse>

    // 상품 스크랩
    @POST("/api/users/{userIdx}/scrabs/product/{productId}")
    fun postProductScrap(
        @Path("userIdx") userIdx: Int,
        @Path("productId") productId: Int
    ): Call<ProductScrapResponse>

    // 상품 상세 화면
    @GET("/api/products/{productId}")
    fun getProductDetail(
        @Path("productId") productId: Int
    ): Call<ProductDetailResponse>
}