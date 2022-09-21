package com.example.risingtest.src.login.byKakao.models

import com.google.gson.annotations.SerializedName

data class PostKakaoLoginRequest (
    @SerializedName("code") val code: String,
)