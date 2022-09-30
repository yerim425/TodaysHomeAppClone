package com.example.risingtest.src.main.store.productDetail.purchase.adapters

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemProductOptionBinding
import com.example.risingtest.src.main.store.productDetail.purchase.datas.ProductOptionItemData

class ProductOptionAdapter(val context: Context): RecyclerView.Adapter<ProductOptionAdapter.ViewHolder>() {

    var list = mutableListOf<ProductOptionItemData>()

    inner class ViewHolder(val binding: ItemProductOptionBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(item: ProductOptionItemData){
            binding.tvProductOption.text = item.title

            binding.layoutProductOption.setOnClickListener {
                val inflater = it.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView = inflater.inflate(R.layout.popupwindow_product_option_detail, null)
                val parentView = it

                val popupWindow = PopupWindow(popupView, it.width, ViewGroup.LayoutParams.WRAP_CONTENT)
                popupWindow.isOutsideTouchable = true
                popupWindow.isFocusable = true


                var location = IntArray(2)
                it.getLocationInWindow(location)

                if(item.optionList.size > 2){ //223
                    var height = 223*item.optionList.size
                    Log.d("height", height.toString())

                    popupWindow.showAsDropDown(it, 0, -height)
                }else{
                    popupWindow.showAsDropDown(it)
                }


                popupWindow.setOnDismissListener{
                    binding.layoutProductOption.background =
                        ContextCompat.getDrawable(context, R.drawable.shape_gray_dark_edge_rounded_3)
                    binding.ivArrowDown.setColorFilter(getColor(context, R.color.login_signup_gray_text_2))

                }

                var detailRv = popupView.findViewById<RecyclerView>(R.id.rv_product_option_detail)
                detailRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                detailRv.adapter = item.optionList?.let{ProductOptionDetailAdapter(it, context, item.price, popupWindow)}

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<ProductOptionItemData>){
        list = nList
    }

}

