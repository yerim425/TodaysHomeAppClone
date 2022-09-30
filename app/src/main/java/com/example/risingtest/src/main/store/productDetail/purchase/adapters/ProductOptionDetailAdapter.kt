package com.example.risingtest.src.main.store.productDetail.purchase.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemProductOptionDetailBinding
import com.example.risingtest.src.main.store.productDetail.models.productDetail.Option
import com.example.risingtest.src.main.store.productDetail.productPurchaseDialog
import com.example.risingtest.src.main.store.productDetail.purchase.dialogs.ProductPurchaseDialog
import java.text.DecimalFormat

class ProductOptionDetailAdapter(
    var optionList: List<Option>, val context: Context, val price: Int, val popupWindow: PopupWindow)
    : RecyclerView.Adapter<ProductOptionDetailAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProductOptionDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Option){
            binding.tvProductOptionDetail.text = item.optionName
            binding.tvProductOptionDetailAmount.text = getDecimalFormat(price + item.optionPrice) +"원"

            binding.layoutProductOptionDetail.setOnClickListener {

                productPurchaseDialog.productOptionSelectedAdapter.addProduct(item)

                popupWindow.dismiss()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductOptionDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(optionList[position])

    }

    override fun getItemCount(): Int {
        return optionList.size
    }

    fun getDecimalFormat(number: Int): String {
        val deciaml = DecimalFormat("#,###")
        return deciaml.format(number)
    }
}