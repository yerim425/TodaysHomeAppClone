package com.example.risingtest.src.orderAndPay

import com.example.risingtest.src.login.byEmail.signUpByEmail.models.EmailSignUpResponse
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.PostEmailSignUpRequest
import com.example.risingtest.src.orderAndPay.models.OrderAndPayResponse
import com.example.risingtest.src.orderAndPay.models.PostOrderAndPayRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderAndPayRetrofitInterface {

    @POST("/api/orders")
    fun postOrderAndPay(@Body params: PostOrderAndPayRequest): Call<OrderAndPayResponse>
}