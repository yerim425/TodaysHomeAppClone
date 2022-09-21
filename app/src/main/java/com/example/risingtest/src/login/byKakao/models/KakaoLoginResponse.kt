package com.example.risingtest.src.login.byKakao.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class KakaoLoginResponse(
    @SerializedName("result") val result: ResultKakaoLogin
) : BaseResponse()
