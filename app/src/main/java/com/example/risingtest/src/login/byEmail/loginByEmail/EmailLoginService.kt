package com.example.risingtest.src.login.byEmail.loginByEmail

import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.login.byEmail.ByEmailRetrofitInterface
import com.example.risingtest.src.login.byEmail.byEmailModels.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailLoginService(val emailLoginFragmentInterface: EmailLoginFragmentInterface) {

    // 전체 유저 정보 요청
    /*fun tryGetUsers(){
        val byEmailRetrofitInterface = ApplicationClass.sRetrofit.create(ByEmailRetrofitInterface::class.java)
        byEmailRetrofitInterface.getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                byEmailFragmentInterface.onGetUserSuccess(response.body() as UserResponse)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                byEmailFragmentInterface.onGetUserFailure(t.message ?: "통신 오류")
            }
        })
    }

    // 회원가입 요청
    fun tryPostSignUp(postSignUpRequest: PostSignUpRequest){
        val byEmailRetrofitInterface = ApplicationClass.sRetrofit.create(ByEmailRetrofitInterface::class.java)
        byEmailRetrofitInterface.postSignUp(postSignUpRequest).enqueue(object :
            Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                byEmailFragmentInterface.onPostSignUpSuccess(response.body() as SignUpResponse)
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                byEmailFragmentInterface.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }*/

    // 로그인 요청
    fun tryPostLogIn(postLogInRequest: PostLogInRequest){
        val byEmailRetrofitInterface = ApplicationClass.sRetrofit.create(ByEmailRetrofitInterface::class.java)
        byEmailRetrofitInterface.postLogIn(postLogInRequest).enqueue(object :
            Callback<LogInResponse> {
            override fun onResponse(call: Call<LogInResponse>, response: Response<LogInResponse>) {
                emailLoginFragmentInterface.onPostLogInSuccess(response.body() as LogInResponse)
            }

            override fun onFailure(call: Call<LogInResponse>, t: Throwable) {
                emailLoginFragmentInterface.onPostLogInFailure(t.message ?: "통신 오류")
            }
        })
    }
}