package com.example.risingtest.src.login.byEmail.signUpByEmail

import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.login.byEmail.ByEmailRetrofitInterface
import com.example.risingtest.src.login.byEmail.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailSignUpService(val emailSignUpFragmentInterface: EmailSignUpFragmentInterface) {


    // 회원가입 요청
    fun tryPostSignUp(postSignUpRequest: PostEmailSignUpRequest){
        val byEmailRetrofitInterface = ApplicationClass.sRetrofit.create(ByEmailRetrofitInterface::class.java)
        byEmailRetrofitInterface.postSignUp(postSignUpRequest).enqueue(object :
            Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                emailSignUpFragmentInterface.onPostSignUpSuccess(response.body() as SignUpResponse)
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                emailSignUpFragmentInterface.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }
}