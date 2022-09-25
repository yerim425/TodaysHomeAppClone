package com.example.risingtest.config

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.risingtest.util.LoadingTextDialog

abstract class BaseActivity<B: ViewBinding>(private val inflate: (LayoutInflater) -> B):
    AppCompatActivity() {
    protected lateinit var binding: B
        private set

    lateinit var loadingTextDialog: LoadingTextDialog
    lateinit var lodingDialog: LoadingTextDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)

    }

    // 토스트를 쉽게 띄울 수 있게 해줌.
    fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    fun showTextLoadingDialog(context: Context, text: String ?= null) {
        loadingTextDialog = LoadingTextDialog(context, text)
        loadingTextDialog.show()
    }

    fun dismissTextLoadingDialog() {
        if (loadingTextDialog.isShowing) {
            loadingTextDialog.dismiss()
        }
    }

    fun showLoadingDialog(context: Context) {
        lodingDialog = LoadingTextDialog(context)
        lodingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (lodingDialog.isShowing) {
            lodingDialog.dismiss()
        }
    }
}