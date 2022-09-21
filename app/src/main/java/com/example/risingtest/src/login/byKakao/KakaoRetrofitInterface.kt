package com.example.risingtest.src.login.byKakao

import com.example.risingtest.src.login.byKakao.models.KakaoLoginResponse
import com.example.risingtest.src.login.byKakao.models.PostKakaoLoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoRetrofitInterface {
    @POST("/api/users/oauth/kakao")
    fun postKakaoLogin(@Body params: PostKakaoLoginRequest) : Call<KakaoLoginResponse>
}