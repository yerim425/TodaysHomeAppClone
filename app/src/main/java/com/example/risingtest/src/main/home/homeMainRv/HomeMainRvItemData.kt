package com.example.risingtest.src.main.home.homeMainRv

import com.example.risingtest.src.main.home.homeMainRv.homeSecondRv.HomePopularPostItemData

data class HomeMainRvItemData(
    val title: String,
    val showDetail: Boolean,
    val viewType: String,
    val postList: MutableList<HomePopularPostItemData>
)
