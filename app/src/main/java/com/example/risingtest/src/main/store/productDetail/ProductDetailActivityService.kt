package com.example.risingtest.src.main.store.productDetail

import android.util.Log
import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.main.models.productScrap.ProductScrapResponse
import com.example.risingtest.src.main.models.productScrapCancel.ProductScrapCancelResponse
import com.example.risingtest.src.main.store.StoreRetrofitInterface
import com.example.risingtest.src.main.store.productDetail.models.productDetail.ProductDetailResponse
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
                if(response.body()?.result != null){
                    Log.d("productDetailActivityService", response.body()!!.result.toString())
                    productDetailInterface.onGetProductDetailSuccess(response.body() as ProductDetailResponse)
                }else{
                    Log.d("productDetailActivityService", "response.body().result is null")
                }
            }

            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                productDetailInterface.onGetProductDetailFailure(t.message ?: "통신 오류")
            }
        })
    }

    // 상품 스크랩 요청
    fun tryPostProductScrap(userIdx: Int, productId: Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.postProductScrap(userIdx, productId).enqueue(object: Callback<ProductScrapResponse>{
            override fun onResponse(
                call: Call<ProductScrapResponse>,
                response: Response<ProductScrapResponse>
            ) {
                if(response.body() != null){
                    productDetailInterface.onPostProductScrapSuccess(response.body() as ProductScrapResponse)
                }else{
                    Log.d("productDetailActivityService", "ProductScrapResponse.body is null")
                }
            }

            override fun onFailure(call: Call<ProductScrapResponse>, t: Throwable) {
                productDetailInterface.onPostProductScrapFailure(t.message ?: "통신 오류")
            }
        })
    }

    // 상품 스크랩 취소 요청
    fun tryPatchProductScrapCancel(userIdx: Int, productId: Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.patchProductScrapCancel(userIdx, productId).enqueue(object : Callback<ProductScrapCancelResponse>{
            override fun onResponse(
                call: Call<ProductScrapCancelResponse>,
                response: Response<ProductScrapCancelResponse>
            ) {
                if(response.body() != null){
                    productDetailInterface.onPatchProductScrapSuccess(response.body() as ProductScrapCancelResponse)
                }else{
                    Log.d("productDetailActivityService", "ProductScrapCancelResponse.body is null")
                }
            }

            override fun onFailure(call: Call<ProductScrapCancelResponse>, t: Throwable) {
                productDetailInterface.onPatchProductScrapFailure(t.message ?: "통신 오류")
            }
        })
    }
}