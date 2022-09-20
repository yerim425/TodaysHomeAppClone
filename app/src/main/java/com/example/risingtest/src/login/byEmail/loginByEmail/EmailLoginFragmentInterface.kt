package com.example.risingtest.src.login.byEmail.loginByEmail

import com.example.risingtest.src.login.byEmail.byEmailModels.LogInResponse
import com.example.risingtest.src.login.byEmail.byEmailModels.SignUpResponse
import com.example.risingtest.src.login.byEmail.byEmailModels.UserResponse

interface EmailLoginFragmentInterface {

    // 유저 로그인 요청 콜백
    fun onPostLogInSuccess(response: LogInResponse)
    fun onPostLogInFailure(message: String)
}