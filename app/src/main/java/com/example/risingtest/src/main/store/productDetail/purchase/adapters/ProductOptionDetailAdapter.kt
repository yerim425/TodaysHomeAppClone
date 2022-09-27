package com.example.risingtest.src.main.store.productDetail.purchase.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListPopupWindow
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemProductOptionDetailBinding
import com.example.risingtest.src.main.store.productDetail.productPurchaseDialog
import com.example.risingtest.src.main.store.productDetail.purchase.datas.ProductOptionDetailItemData
import com.example.risingtest.src.main.store.productDetail.purchase.datas.ProductOptionSelectedItemData

class ProductOptionDetailAdapter(var optionList: MutableList<ProductOptionDetailItemData>, val context: Context, val popupWindow: PopupWindow)
    : RecyclerView.Adapter<ProductOptionDetailAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProductOptionDetailBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProductOptionDetailItemData){
            binding.tvProductOptionDetail.text = item.title
            binding.tvProductOptionDetailAmount.text = item.price

            binding.layoutProductOptionDetail.setOnClickListener {

                productPurchaseDialog.productOptionSelectedAdapter.addProduct(
                    ProductOptionSelectedItemData(item.title, item.price)
                )

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


}