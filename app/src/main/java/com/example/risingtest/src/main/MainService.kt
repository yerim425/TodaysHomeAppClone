package com.example.risingtest.src.main

import android.util.Log
import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.main.models.myprofile.MyProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainService(val mainActivityInterface: MainActivityInterface) {

    // 자신의 정보 조회 요청
    fun tryGetMyProfile(userIdx: Int){
        val splashRetrofitInterface = ApplicationClass.sRetrofit.create(MainRetrofitInterface::class.java)
        splashRetrofitInterface.getMyProfile(userIdx).enqueue(object: Callback<MyProfileResponse>{
            override fun onResponse(
                call: Call<MyProfileResponse>,
                response: Response<MyProfileResponse>
            ) {
                if(response.body() != null){
                    Log.d("mainService", response.body()?.result?.nickname.toString())
                    mainActivityInterface.onGetMyProfileSuccess(response.body() as MyProfileResponse)

                }
            }

            override fun onFailure(call: Call<MyProfileResponse>, t: Throwable) {

                mainActivityInterface.onGetMyProfileFailure(t.message ?: "통신 오류")
            }
        })
    }
}