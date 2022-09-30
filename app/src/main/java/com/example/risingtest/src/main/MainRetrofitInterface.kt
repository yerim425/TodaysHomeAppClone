package com.example.risingtest.src.main

import com.example.risingtest.src.main.models.myprofile.MyProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MainRetrofitInterface {
    @GET("/api/users/myProfile/{userIdx}")
    fun getMyProfile(
        @Path("userIdx") userIdx: Int,
    ) : Call<MyProfileResponse>
}