package com.example.risingtest.src.login.byEmail

import com.example.risingtest.src.login.byEmail.byEmailModels.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ByEmailRetrofitInterface {
    @GET("/api/users")
    fun getUsers() : Call<UserResponse>

    @POST("/api/users")
    fun postSignUp(@Body params: PostSignUpRequest): Call<SignUpResponse>

    @POST("/api/users/log-in")
    fun postLogIn(@Body params: PostLogInRequest): Call<LogInResponse>
}