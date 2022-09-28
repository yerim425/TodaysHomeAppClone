package com.example.risingtest.src.main.store

import com.example.risingtest.src.main.store.productDetail.models.ProductDetailResponse
import com.example.risingtest.src.main.store.models.productScrap.ProductScrapResponse
import com.example.risingtest.src.main.store.models.storePage.StorePageResponse

interface StoreFragmentInterface {

    // 스토어 화면 요청
    fun onGetStorePageSuccess(response: StorePageResponse)
    fun onGetStorePageFailure(message: String)

    // 상품 스크랩 요청
    fun onPostProductScrapSuccess(response: ProductScrapResponse)
    fun onPostProductScrapFailure(message: String)

    // 상품 상세 화면 요청
    fun onGetProductDetailSuccess(response: ProductDetailResponse)
    fun onGetProductDetailFailure(message: String)
}