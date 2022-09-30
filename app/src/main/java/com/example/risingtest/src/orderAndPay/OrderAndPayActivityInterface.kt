package com.example.risingtest.src.orderAndPay

import com.example.risingtest.src.orderAndPay.models.OrderAndPayResponse

interface OrderAndPayActivityInterface {

    fun onPostOrderAndPaySuccess(response: OrderAndPayResponse)
    fun onPostOrderAndPayFailure(message: String)
}