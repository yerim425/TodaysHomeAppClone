package com.example.risingtest.src.login.byEmail.loginByEmail.models

import com.google.gson.annotations.SerializedName

data class ResultEmailLogin(
        @SerializedName("userIdx") val userIdx: Int,
        @SerializedName("jwt") val jwt: String
)