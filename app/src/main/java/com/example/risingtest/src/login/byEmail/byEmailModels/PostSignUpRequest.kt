package com.example.risingtest.src.login.byEmail.byEmailModels

import com.google.gson.annotations.SerializedName

data class PostSignUpRequest(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
        @SerializedName("nickname") val nickname: String,
        @SerializedName("isMarketing") val isMarketing: String,
        @SerializedName("isSMS") val isSMS: String,
)