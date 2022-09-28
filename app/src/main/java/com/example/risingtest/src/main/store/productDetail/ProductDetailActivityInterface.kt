package com.example.risingtest.src.main.store.productDetail

import com.example.risingtest.src.main.store.productDetail.models.ProductDetailResponse

interface ProductDetailActivityInterface {

    // 상품 상세 화면 요청
    fun onGetProductDetailSuccess(response: ProductDetailResponse)
    fun onGetProductDetailFailure(message: String)
}