package com.example.risingtest.src.login.byEmail.signUpByEmail

import com.example.risingtest.src.login.byEmail.models.SignUpResponse

interface EmailSignUpFragmentInterface {

    // 유저 회원가입 요청 콜백
    fun onPostSignUpSuccess(response: SignUpResponse)
    fun onPostSignUpFailure(message: String)
}