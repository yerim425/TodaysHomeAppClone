package com.example.risingtest.src.orderAndPay.orderProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemOrderProductBinding
import java.text.DecimalFormat

class OrderProductAdapter: RecyclerView.Adapter<OrderProductAdapter.ViewHolder>() {

    var list = mutableListOf<OrderProductItemData>()

    inner class ViewHolder(val binding: ItemOrderProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: OrderProductItemData){

            binding.tvBrand.text = item.brandName
            Glide.with(binding.ivProdImg)
                .load(item.imageUrl)
                .placeholder(R.drawable.shape_gray2_view_rounded_5)
                .error(R.drawable.shape_gray2_view_rounded_5)
                .fallback(R.drawable.shape_gray2_view_rounded_5)
                .override(160  , 160)
                .centerCrop()
                .apply(RequestOptions.centerCropTransform())
                .into(binding.ivProdImg)
            binding.tvProdName.text = item.productName
            binding.tvOptionName.text = "· " + item.optionName
            binding.tvProdPrice.text = getDecimalFormat(item.price) + "원"
            binding.tvProdNum.text = item.productNum.toString() + "개"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemOrderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<OrderProductItemData>){
        list = nList
    }

    fun getDecimalFormat(number: Int): String {
        val deciaml = DecimalFormat("#,###")
        return deciaml.format(number)
    }
}