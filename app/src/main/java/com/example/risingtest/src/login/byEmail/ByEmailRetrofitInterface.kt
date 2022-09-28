package com.example.risingtest.src.login.byEmail

import com.example.risingtest.src.login.byEmail.loginByEmail.models.EmailLogInResponse
import com.example.risingtest.src.login.byEmail.loginByEmail.models.PostEmailLogInRequest
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.EmailSignUpResponse
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.PostEmailSignUpRequest
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ByEmailRetrofitInterface {
    @GET("/api/users")
    fun getUsers() : Call<UserResponse>

    @POST("/api/users/sign-up")
    fun postSignUp(@Body params: PostEmailSignUpRequest): Call<EmailSignUpResponse>

    @POST("/api/users/log-in")
    fun postLogIn(@Body params: PostEmailLogInRequest): Call<EmailLogInResponse>
}