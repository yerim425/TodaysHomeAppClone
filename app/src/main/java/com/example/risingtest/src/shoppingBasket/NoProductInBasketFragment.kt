package com.example.risingtest.src.shoppingBasket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentNoProductInBasketBinding
import com.example.risingtest.src.main.store.StoreFragment

class NoProductInBasketFragment : BaseFragment<FragmentNoProductInBasketBinding>(
    FragmentNoProductInBasketBinding::bind, R.layout.fragment_no_product_in_basket
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoToPick.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frm_main, StoreFragment()).commit()
        }



    }

}