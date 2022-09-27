package com.example.risingtest.src.shoppingBasket.shoppingBasketFragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentShoppingBasketBinding
import com.example.risingtest.src.main.store.storeMainRv.storeSecondRv.ProductItemData
import com.example.risingtest.src.orderAndPay.OrderAndPayActivity
import com.example.risingtest.src.shoppingBasket.shoppingBasketFragment.shoppingBasketOtherProduct.ShoppingBasketOtherProductAdapter
import com.example.risingtest.src.shoppingBasket.shoppingBasketFragment.shoppingBasketProduct.ShoppingBasketProductAdapter
import com.example.risingtest.src.shoppingBasket.shoppingBasketFragment.shoppingBasketProduct.ShoppingBasketProductItemData

class ShoppingBasketFragment : BaseFragment<FragmentShoppingBasketBinding>(
    FragmentShoppingBasketBinding::bind, R.layout.fragment_shopping_basket) {

    lateinit var shoppingBasketProductAdapter: ShoppingBasketProductAdapter
    lateinit var shoppingBasketOtherProductAdapter: ShoppingBasketOtherProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shoppingBasketProductAdapter = ShoppingBasketProductAdapter()
        shoppingBasketProductAdapter.getListFromView(setShoppingBasketProductList())
        binding.rvShoppingBasket.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvShoppingBasket.adapter = shoppingBasketProductAdapter


        shoppingBasketOtherProductAdapter = ShoppingBasketOtherProductAdapter()
        shoppingBasketOtherProductAdapter.getListFromView(setShoppingBasketOtherProductList())
        binding.rvOtherProducts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvOtherProducts.adapter = shoppingBasketOtherProductAdapter


        binding.btnPurchase.setOnClickListener {
            val intent = Intent(requireContext(), OrderAndPayActivity::class.java)
            intent.putExtra("newActivity", R.drawable.anim_slide_in_right.toString())
            intent.putExtra("preActivity", R.drawable.anim_slide_out_left.toString())
            requireActivity().startActivity(intent)
        }
    }

    fun setShoppingBasketProductList(): MutableList<ShoppingBasketProductItemData>{

        var list = mutableListOf<ShoppingBasketProductItemData>()
        list.add(ShoppingBasketProductItemData())
        list.add(ShoppingBasketProductItemData())
        list.add(ShoppingBasketProductItemData())
        return list

    }

    fun setShoppingBasketOtherProductList(): MutableList<ProductItemData>{

        var list = mutableListOf<ProductItemData>()
        list.add(ProductItemData())
        list.add(ProductItemData())
        list.add(ProductItemData())
        list.add(ProductItemData())
        list.add(ProductItemData())
        return list
    }
}