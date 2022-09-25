package com.example.risingtest.src.login.byEmail.loginByEmail.models

import com.google.gson.annotations.SerializedName

data class PostEmailLogInRequest(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
)