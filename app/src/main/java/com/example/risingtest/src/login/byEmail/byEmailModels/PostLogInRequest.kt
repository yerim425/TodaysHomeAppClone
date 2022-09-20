package com.example.risingtest.src.login.byEmail.byEmailModels

import com.google.gson.annotations.SerializedName

data class PostLogInRequest(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
)