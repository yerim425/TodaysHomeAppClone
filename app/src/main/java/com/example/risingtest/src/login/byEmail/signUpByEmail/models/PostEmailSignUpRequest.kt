package com.example.risingtest.src.login.byEmail.signUpByEmail.models

import com.google.gson.annotations.SerializedName

data class PostEmailSignUpRequest(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
        @SerializedName("nickname") val nickname: String,
        @SerializedName("isMarketing") val isMarketing: String,
        @SerializedName("isReceive") val isReceive: String,
)