package com.example.risingtest.src.main.store

import android.util.Log
import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.main.store.productDetail.models.ProductDetailResponse
import com.example.risingtest.src.main.store.models.productScrap.ProductScrapResponse
import com.example.risingtest.src.main.store.models.storePage.StorePageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreFragmentService(val storeFragmentInterface: StoreFragmentInterface) {

    // 스토어 화면 요청
    fun tryGetStorePage(userIdx: Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.getMyProfile(userIdx).enqueue(object: Callback<StorePageResponse> {
            override fun onResponse(
                call: Call<StorePageResponse>,
                response: Response<StorePageResponse>
            ) {
                if(response.body() != null){
                    Log.d("StoreService", response.body()?.result.toString())
                    storeFragmentInterface.onGetStorePageSuccess(response.body() as StorePageResponse)
                }else{
                    Log.d("StoreService", "StroePageResponse.body is null")
                }
            }

            override fun onFailure(call: Call<StorePageResponse>, t: Throwable) {

                storeFragmentInterface.onGetStorePageFailure(t.message ?: "통신 오류")
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
                    storeFragmentInterface.onPostProductScrapSuccess(response.body() as ProductScrapResponse)
                }else{
                    Log.d("StoreService", "ProductScrapResponse.body is null")
                }
            }

            override fun onFailure(call: Call<ProductScrapResponse>, t: Throwable) {
                storeFragmentInterface.onPostProductScrapFailure(t.message ?: "통신 오류")
            }
        })
    }

    // 상품 상세 화면 요청
    fun tryGetProductDetail(productId: Int){
        val storeRetrofitInterface = ApplicationClass.sRetrofit.create(StoreRetrofitInterface::class.java)
        storeRetrofitInterface.getProductDetail(productId).enqueue(object: Callback<ProductDetailResponse>{
            override fun onResponse(
                call: Call<ProductDetailResponse>,
                response: Response<ProductDetailResponse>
            ) {
                storeFragmentInterface.onGetProductDetailSuccess(response.body() as ProductDetailResponse)
            }

            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                storeFragmentInterface.onGetProductDetailFailure(t.message ?: "통신 오류")
            }
        })
    }
}