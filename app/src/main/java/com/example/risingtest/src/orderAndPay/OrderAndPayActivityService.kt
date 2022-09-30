package com.example.risingtest.src.orderAndPay

import android.util.Log
import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.src.login.byEmail.ByEmailRetrofitInterface
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.EmailSignUpResponse
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.PostEmailSignUpRequest
import com.example.risingtest.src.main.MainActivityInterface
import com.example.risingtest.src.orderAndPay.models.OrderAndPayResponse
import com.example.risingtest.src.orderAndPay.models.PostOrderAndPayRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class OrderAndPayActivityService(val orderAndPayActivityInterface: OrderAndPayActivityInterface) {

    fun tryPostOrderAndPay(postOrderAndPayRequest: PostOrderAndPayRequest){
        val orderAndPayRetrofitInterface = ApplicationClass.sRetrofit.create(OrderAndPayRetrofitInterface::class.java)
        orderAndPayRetrofitInterface.postOrderAndPay(postOrderAndPayRequest).enqueue(object : Callback<OrderAndPayResponse>{

            override fun onResponse(call: Call<OrderAndPayResponse>, response: Response<OrderAndPayResponse>) {
                Log.d("EmailSignUpService", response.body()?.result.toString())
                if(response.body()?.result != null){
                    orderAndPayActivityInterface.onPostOrderAndPaySuccess(response.body() as OrderAndPayResponse)
                }
            }

            override fun onFailure(call: Call<OrderAndPayResponse>, t: Throwable) {
                orderAndPayActivityInterface.onPostOrderAndPayFailure(t.message ?: "통신 오류")
            }
        })
    }

}