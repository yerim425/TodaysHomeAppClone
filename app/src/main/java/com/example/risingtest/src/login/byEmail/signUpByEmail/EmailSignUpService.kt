package com.example.risingtest.src.login.byEmail.signUpByEmail

import android.util.Log
import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.login.byEmail.ByEmailRetrofitInterface
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.EmailSignUpResponse
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.PostEmailSignUpRequest
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class EmailSignUpService(val emailSignUpFragmentInterface: EmailSignUpFragmentInterface) {


    // 회원가입 요청
    fun tryPostSignUp(postSignUpRequest: PostEmailSignUpRequest){
        val byEmailRetrofitInterface = ApplicationClass.sRetrofit.create(ByEmailRetrofitInterface::class.java)
        byEmailRetrofitInterface.postSignUp(postSignUpRequest).enqueue(object :
            Callback<EmailSignUpResponse> {
            override fun onResponse(call: Call<EmailSignUpResponse>, response: Response<EmailSignUpResponse>) {
                Log.d("EmailSignUpService", response.body()?.result.toString())
                if(response.body()?.result != null){
                    emailSignUpFragmentInterface.onPostSignUpSuccess(response.body() as EmailSignUpResponse)
                }
            }

            override fun onFailure(call: Call<EmailSignUpResponse>, t: Throwable) {
                emailSignUpFragmentInterface.onPostSignUpFailure(t.message ?: "통신 오류")
            }
        })
    }

    // 모든 유저 조회 요청
    fun tryGetUser(){
        val byEmailRetrofitInterface = ApplicationClass.sRetrofit.create(ByEmailRetrofitInterface::class.java)
        byEmailRetrofitInterface.getUsers().enqueue(object: Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                emailSignUpFragmentInterface.onGetUserSuccess(response.body() as UserResponse)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                emailSignUpFragmentInterface.onGetUserFailure(t.message ?: "통신 오류")
            }
        })
    }


}