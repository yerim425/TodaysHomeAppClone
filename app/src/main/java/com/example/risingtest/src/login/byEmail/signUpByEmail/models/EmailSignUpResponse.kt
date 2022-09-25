package com.example.risingtest.src.login.byEmail.signUpByEmail.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class EmailSignUpResponse(
        @SerializedName("result") val result: ResultEmailSignUp
) : BaseResponse()