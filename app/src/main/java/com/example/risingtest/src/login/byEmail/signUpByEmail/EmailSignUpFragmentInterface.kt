package com.example.risingtest.src.login.byEmail.signUpByEmail

import com.example.risingtest.src.login.byEmail.signUpByEmail.models.EmailSignUpResponse
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.UserResponse

interface EmailSignUpFragmentInterface {

    // 유저 회원가입 요청 콜백
    fun onPostSignUpSuccess(response: EmailSignUpResponse)
    fun onPostSignUpFailure(message: String)

    // 모든 유저 조회 요청 콜백
    fun onGetUserSuccess(response: UserResponse)
    fun onGetUserFailure(message: String)
}