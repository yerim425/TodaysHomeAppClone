package com.example.risingtest.src.orderAndPay

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.risingtest.databinding.DialogLoadingBinding
import com.example.risingtest.databinding.DialogLoadingTextBinding
import com.example.risingtest.databinding.DialogOrderCancelBinding

class OrderCancelDialog(context: Context, val activity: OrderAndPayActivity): Dialog(context) {
    private lateinit var binding: DialogOrderCancelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogOrderCancelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        //window!!.setBackgroundDrawable(ColorDrawable())

        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnYes.setOnClickListener {
            dismiss()
            activity.finish()
        }

    }

    override fun show() {
        if(!this.isShowing) super.show()
    }

}