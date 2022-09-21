package com.example.risingtest.src.login.byKakao.models

import com.google.gson.annotations.SerializedName

data class ResultKakaoLogin(
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("jwt") val jwt: String
)