package com.example.risingtest.src.login.byEmail.signUpByEmail.models

import com.google.gson.annotations.SerializedName

data class ResultEmailSignUp(
        @SerializedName("userIdx") val userIdx: Int,
        @SerializedName("jwt") val jwt: String
)