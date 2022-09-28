package com.example.risingtest.src.main

import com.example.risingtest.src.main.models.MyProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainRetrofitInterface {
    @GET("/api/users/myProfile/{userIdx}")
    fun getMyProfile(
        @Path("userIdx") userIdx: Int,
    ) : Call<MyProfileResponse>
}