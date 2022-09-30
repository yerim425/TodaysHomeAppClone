package com.example.risingtest.src.scrap.fragments.scrapAll

import android.util.Log
import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.scrap.ScrapRetrofitInterface
import com.example.risingtest.src.scrap.fragments.scrapAll.scrapedAllItem.ScrapedAllItemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScrapedAllService(val scrapedAllFragmentInterface: ScrapedAllFragmentInterface) {

    fun tryGetScrapedAllItem(userIdx: Int){
        val scrapRetrofitInterface = ApplicationClass.sRetrofit.create(ScrapRetrofitInterface::class.java)
        scrapRetrofitInterface.getScrapedAllItem(userIdx).enqueue(object : Callback<ScrapedAllItemResponse>{
            override fun onResponse(
                call: Call<ScrapedAllItemResponse>,
                response: Response<ScrapedAllItemResponse>
            ) {
                if(response.body() != null){
                    Log.d("scrapActivityService", response.body()?.result.toString())
                    scrapedAllFragmentInterface.onGetScrapedAllItemSuccess(response.body() as ScrapedAllItemResponse)
                }else{
                    Log.d("scrapActivityService", "ScrapedAllItemResponse.body is null")
                }
            }

            override fun onFailure(call: Call<ScrapedAllItemResponse>, t: Throwable) {
                scrapedAllFragmentInterface.onGetScrapedAllItemFailure(t.message ?: "통신 오류")

            }
        })
    }


}