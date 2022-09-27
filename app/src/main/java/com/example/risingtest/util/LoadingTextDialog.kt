package com.example.risingtest.util

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

class LoadingTextDialog(context: Context, var text: String?= null): Dialog(context) {
    private lateinit var binding: DialogLoadingTextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogLoadingTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        //window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(text != null){
            binding.tvLoading.visibility = View.VISIBLE
            binding.tvLoading.text = text
        }else{
            binding.tvLoading.visibility = View.GONE
            binding.tvLoading.text = ""
        }
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }

}