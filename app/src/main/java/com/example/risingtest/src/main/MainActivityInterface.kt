package com.example.risingtest.src.main

import com.example.risingtest.src.main.models.MyProfileResponse


interface MainActivityInterface {
    // 유저 정보 요청
    fun onGetMyProfileSuccess(response: MyProfileResponse)
    fun onGetMyProfileFailure(message: String)
}