package com.example.risingtest.src.login.byEmail.loginByEmail

import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.login.byEmail.ByEmailRetrofitInterface
import com.example.risingtest.src.login.byEmail.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailLoginService(val emailLoginFragmentInterface: EmailLoginFragmentInterface) {


    // 로그인 요청
    fun tryPostLogIn(postLogInRequest: PostEmailLogInRequest){
        val byEmailRetrofitInterface = ApplicationClass.sRetrofit.create(ByEmailRetrofitInterface::class.java)
        byEmailRetrofitInterface.postLogIn(postLogInRequest).enqueue(object :
            Callback<EmailLogInResponse> {
            override fun onResponse(call: Call<EmailLogInResponse>, response: Response<EmailLogInResponse>) {
                emailLoginFragmentInterface.onPostLogInSuccess(response.body() as EmailLogInResponse)
            }

            override fun onFailure(call: Call<EmailLogInResponse>, t: Throwable) {
                emailLoginFragmentInterface.onPostLogInFailure(t.message ?: "통신 오류")
            }
        })
    }
}