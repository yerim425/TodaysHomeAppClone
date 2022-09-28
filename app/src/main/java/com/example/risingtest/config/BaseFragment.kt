package com.example.risingtest.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.risingtest.R
import com.example.risingtest.util.LoadingDialog
import com.example.risingtest.util.LoadingTextDialog
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null

    lateinit var loadingTextDialog: LoadingTextDialog
    lateinit var lodingDialog: LoadingDialog
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun backFragment(){
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
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
        lodingDialog = LoadingDialog(context)
        lodingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (lodingDialog.isShowing) {
            lodingDialog.dismiss()
        }
    }



}