package com.example.risingtest.src.orderAndPay.orderResults

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.risingtest.config.BaseActivity
import com.example.risingtest.databinding.ActivityOrderResultBinding

class OrderResultActivity : BaseActivity<ActivityOrderResultBinding>(ActivityOrderResultBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvAccountNumber.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnContinueShopping.setOnClickListener {
            finish()
        }
    }
}