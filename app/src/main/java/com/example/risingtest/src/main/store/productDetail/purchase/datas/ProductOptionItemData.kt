package com.example.risingtest.src.main.store.productDetail.purchase.datas

import com.example.risingtest.src.main.store.productDetail.models.productDetail.Option

data class ProductOptionItemData(
    val title: String,
    val price: Int,
    val optionList: List<Option>
)
