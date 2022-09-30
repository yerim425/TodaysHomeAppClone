package com.example.risingtest.src.scrap.fragments.scrapProduct

import android.util.Log
import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.scrap.ScrapRetrofitInterface
import com.example.risingtest.src.scrap.fragments.scrapProduct.scrapedProductItem.ScrapedProductItemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScrapedProductService(val scrapedProductFragmentInterface: ScrapedProductFragmentInterface) {



    fun tryGetScrapedProductItem(userIdx: Int){
        val scrapRetrofitInterface = ApplicationClass.sRetrofit.create(ScrapRetrofitInterface::class.java)
        scrapRetrofitInterface.getScrapedProductItem(userIdx).enqueue(object : Callback<ScrapedProductItemResponse>{
            override fun onResponse(
                call: Call<ScrapedProductItemResponse>,
                response: Response<ScrapedProductItemResponse>
            ) {
                if(response.body() != null){
                    Log.d("scrapActivityService", response.body()?.result.toString())
                    scrapedProductFragmentInterface.onGetScrapedProductItemSuccess(response.body() as ScrapedProductItemResponse)
                }else{
                    Log.d("scrapActivityService", "ScrapedProductItemResponse.body is null")
                }
            }

            override fun onFailure(call: Call<ScrapedProductItemResponse>, t: Throwable) {
                scrapedProductFragmentInterface.onGetScrapedProductItemFailure(t.message ?: "통신 오류")

            }
        })
    }
}