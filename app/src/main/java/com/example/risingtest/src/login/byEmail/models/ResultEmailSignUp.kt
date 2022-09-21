package com.example.risingtest.src.login.byEmail.models

import com.google.gson.annotations.SerializedName

data class ResultEmailSignUp(
        @SerializedName("userId") val userId: Int,
        @SerializedName("jwt") val jwt: String
)