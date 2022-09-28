package com.example.risingtest.src.main.store.storeMainRv

import com.example.risingtest.src.main.store.models.storePage.StorePageProduct

data class StoreMainRvItemData(
    val title: String,
    val showSeeMoreView: Boolean,
    val viewType: String,
    val produceList: MutableList<StorePageProduct>
)
