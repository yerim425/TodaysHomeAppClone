package com.example.risingtest.src.login.byEmail.loginByEmail

import android.provider.Settings.System.getString
import android.util.Log
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.login.byEmail.ByEmailRetrofitInterface
import com.example.risingtest.src.login.byEmail.loginByEmail.models.EmailLogInResponse
import com.example.risingtest.src.login.byEmail.loginByEmail.models.PostEmailLogInRequest
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
                Log.d("EmailLoginService", response.body()?.code.toString())
                if(response.body()?.result != null){
                    emailLoginFragmentInterface.onPostLogInSuccess(response.body() as EmailLogInResponse)
                }else{
                    emailLoginFragmentInterface.onPostLogInFailure("아이디 또는 비밀번호가 잘못되었습니다.")
                }
            }

            override fun onFailure(call: Call<EmailLogInResponse>, t: Throwable) {
                emailLoginFragmentInterface.onPostLogInFailure(t.message ?: "통신 오류")
            }
        })
    }

}