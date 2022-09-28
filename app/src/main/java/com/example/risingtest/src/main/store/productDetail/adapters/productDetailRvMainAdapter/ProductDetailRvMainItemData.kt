package com.example.risingtest.src.main.store.productDetail.adapters.productDetailRvMainAdapter

import com.example.risingtest.src.main.store.storeMainRv.storeProductRv.StoreProductItemData

data class ProductDetailRvMainItemData(
    val title: String,
    val isDetail: Boolean,
    val num: Int?= null,
    var secondList: MutableList<StoreProductItemData>?= null
)
