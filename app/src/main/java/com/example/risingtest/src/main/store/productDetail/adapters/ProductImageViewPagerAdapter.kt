package com.example.risingtest.src.main.store.productDetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemViewpagerBaseBinding

class ProductImageViewPagerAdapter: RecyclerView.Adapter<ProductImageViewPagerAdapter.ViewHolder>() {
    var pageList = mutableListOf<Int>()

    inner class ViewHolder(val binding: ItemViewpagerBaseBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Int){
            binding.ivViewpager.setImageResource(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewpagerBaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductImageViewPagerAdapter.ViewHolder, position: Int) {
        holder.bind(pageList[position])
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    fun getListOfView(nList: MutableList<Int>){
        pageList = nList
    }

}