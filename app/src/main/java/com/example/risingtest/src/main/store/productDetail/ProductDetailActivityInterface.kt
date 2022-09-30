package com.example.risingtest.src.main.store.productDetail

import com.example.risingtest.src.main.models.productScrap.ProductScrapResponse
import com.example.risingtest.src.main.models.productScrapCancel.ProductScrapCancelResponse
import com.example.risingtest.src.main.store.productDetail.models.productDetail.ProductDetailResponse

interface ProductDetailActivityInterface {

    // 상품 상세 화면 요청
    fun onGetProductDetailSuccess(response: ProductDetailResponse)
    fun onGetProductDetailFailure(message: String)

    // 상품 스크랩 요청
    fun onPostProductScrapSuccess(response: ProductScrapResponse)
    fun onPostProductScrapFailure(message: String)

    // 상품 스크랩 취소 요청
    fun onPatchProductScrapSuccess(response: ProductScrapCancelResponse)
    fun onPatchProductScrapFailure(message: String)
}