package com.example.risingtest.src.login.byKakao

import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.login.MainLoginFragmentInterface
import com.example.risingtest.src.login.byKakao.models.KakaoLoginResponse
import com.example.risingtest.src.login.byKakao.models.PostKakaoLoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoLoginService(val mainLoginFragmentInterface: MainLoginFragmentInterface) {


    // 카카오 로그인 요청
    fun tryPostKakaoLogin(postKakaoLoginRequest: PostKakaoLoginRequest){
        val kakaoLoginRetrofitInterface = ApplicationClass.sRetrofit.create(KakaoRetrofitInterface::class.java)
        kakaoLoginRetrofitInterface.postKakaoLogin(postKakaoLoginRequest).enqueue(object :
            Callback<KakaoLoginResponse>{
            override fun onResponse(call: Call<KakaoLoginResponse>, response: Response<KakaoLoginResponse>) {
                mainLoginFragmentInterface.onPostKakaoLogInSuccess(response.body() as KakaoLoginResponse)
            }
            override fun onFailure(call: Call<KakaoLoginResponse>, t: Throwable) {
                mainLoginFragmentInterface.onPostKakaoLogInFailure(t.message ?: "통신 오류")
            }
        })
    }
}