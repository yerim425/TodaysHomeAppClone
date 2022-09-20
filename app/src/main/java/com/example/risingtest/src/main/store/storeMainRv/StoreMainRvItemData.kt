package com.example.risingtest.src.main.store.storeMainRv

import com.example.risingtest.src.main.store.storeMainRv.storeSecondRv.ProductItemData

data class StoreMainRvItemData(
    val title: Int,
    val showSeeMoreView: Boolean,
    val viewType: String,
    val produceList: MutableList<ProductItemData>
)
