package com.example.risingtest.src.shoppingBasket.shoppingBasketFragment.shoppingBasketOtherProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemRvProductBaseBinding
import com.example.risingtest.src.main.store.storeMainRv.storeSecondRv.ProductItemData

class ShoppingBasketOtherProductAdapter: RecyclerView.Adapter<ShoppingBasketOtherProductAdapter.ViewHolder>() {

    var list = mutableListOf<ProductItemData>()

    inner class ViewHolder(val binding: ItemRvProductBaseBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProductItemData){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRvProductBaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<ProductItemData>){
        list = nList
    }

}