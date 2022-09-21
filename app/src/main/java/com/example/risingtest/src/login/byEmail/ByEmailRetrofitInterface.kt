package com.example.risingtest.src.login.byEmail

import com.example.risingtest.src.login.byEmail.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ByEmailRetrofitInterface {
    //@GET("/api/users")
    //fun getUsers() : Call<UserResponse>

    @POST("/api/users")
    fun postSignUp(@Body params: PostEmailSignUpRequest): Call<SignUpResponse>

    @POST("/api/users/log-in")
    fun postLogIn(@Body params: PostEmailLogInRequest): Call<EmailLogInResponse>
}