package com.example.risingtest.src.shoppingBasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.risingtest.R
import com.example.risingtest.config.BaseActivity
import com.example.risingtest.databinding.ActivityShoppingBasketBinding

class ShoppingBasketActivity : BaseActivity<ActivityShoppingBasketBinding>(ActivityShoppingBasketBinding::inflate) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 엑티비티 에니메이션
        overridePendingTransition(R.drawable.anim_slide_in_right, R.drawable.anim_slide_out_left)


        // 장바구니에 담은 상품이 없으면 noProductInBasketFragment
        // 있으면 ShoppingBasketFragment


        binding.ivCancelAppbar.setOnClickListener {
            finish()
        }
    }
}