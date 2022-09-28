package com.example.risingtest.src.login.byEmail.loginByEmail

import com.example.risingtest.src.login.byEmail.loginByEmail.models.EmailLogInResponse

interface EmailLoginFragmentInterface {

    // 유저 로그인 요청 콜백
    fun onPostLogInSuccess(response: EmailLogInResponse)
    fun onPostLogInFailure(message: String)

}