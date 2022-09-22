package com.example.risingtest.src.shoppingBasket.shoppingBasketFragment.shoppingBasketProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemShoppingBasketProductBinding

class ShoppingBasketProductAdapter: RecyclerView.Adapter<ShoppingBasketProductAdapter.ViewHolder>() {

    var list = mutableListOf<ShoppingBasketProductItemData>()

    inner class ViewHolder(val binding: ItemShoppingBasketProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ShoppingBasketProductItemData){
            Glide.with(binding.ivProductImg)
                .load(R.drawable.img_prod1_1)
                .override(380  , 380)
                .centerCrop()
                .apply(RequestOptions.centerCropTransform())
                .into(binding.ivProductImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemShoppingBasketProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<ShoppingBasketProductItemData>){
        list = nList
    }
}