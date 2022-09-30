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

        binding.tvOrderProduct.text = intent.getStringExtra("optionName")
        binding.tvDeliveryDestination.text = intent.getStringExtra("address")
        binding.tvPaymentAmount.text = intent.getStringExtra("price")
        binding.tvDepositAmount.text = intent.getStringExtra("price")
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnContinueShopping.setOnClickListener {
            finish()
        }
    }
}