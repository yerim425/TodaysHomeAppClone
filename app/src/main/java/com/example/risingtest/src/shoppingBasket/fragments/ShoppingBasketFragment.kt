package com.example.risingtest.src.shoppingBasket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentShoppingBasketBinding

class ShoppingBasketFragment : BaseFragment<FragmentShoppingBasketBinding>(
    FragmentShoppingBasketBinding::bind, R.layout.fragment_shopping_basket
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}