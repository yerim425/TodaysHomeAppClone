package com.example.risingtest.src.login.byEmail.signUpByEmail.models

import com.google.gson.annotations.SerializedName

data class ResultUser(
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("status") val status: String,
)
