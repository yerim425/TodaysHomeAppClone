package com.example.risingtest.src.login.byEmail.signUpByEmail.models

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse

data class UserResponse(
        // 베이스 리스폰스를 상속 받았으므로, 아래 내용은 포함이 되었습니다.
        @SerializedName("result") val result: ArrayList<ResultUser>
): BaseResponse()