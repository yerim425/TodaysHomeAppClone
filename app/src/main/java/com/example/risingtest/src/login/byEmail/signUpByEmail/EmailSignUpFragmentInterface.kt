package com.example.risingtest.src.login.byEmail.signUpByEmail

import com.example.risingtest.src.login.byEmail.byEmailModels.LogInResponse
import com.example.risingtest.src.login.byEmail.byEmailModels.SignUpResponse
import com.example.risingtest.src.login.byEmail.byEmailModels.UserResponse

interface EmailSignUpFragmentInterface {

    // 유저 회원가입 요청 콜백
    fun onPostSignUpSuccess(response: SignUpResponse)
    fun onPostSignUpFailure(message: String)
}