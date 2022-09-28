package com.example.risingtest.src.main.store.productDetail

import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.main.store.StoreRetrofitInterface
import com.example.risingtest.src.main.store.productDetail.models.ProductDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailActivityService(val productDetailInterface: ProductDetailActivityInterface) {

    // 상품 상세 화면 요청
    fun tryGetProductDetail(productId: Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.getProductDetail(productId).enqueue(object: Callback<ProductDetailResponse>{
            override fun onResponse(
                call: Call<ProductDetailResponse>,
                response: Response<ProductDetailResponse>
            ) {
                productDetailInterface.onGetProductDetailSuccess(response.body() as ProductDetailResponse)
            }

            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                productDetailInterface.onGetProductDetailFailure(t.message ?: "통신 오류")
            }
        })
    }
}