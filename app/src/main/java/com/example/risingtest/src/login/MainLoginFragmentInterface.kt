package com.example.risingtest.src.login

import com.example.risingtest.src.login.byKakao.models.KakaoLoginResponse

interface MainLoginFragmentInterface {

    // 카카오 로그인 요청 콜백
    fun onPostKakaoLogInSuccess(response: KakaoLoginResponse)
    fun onPostKakaoLogInFailure(message: String)
}