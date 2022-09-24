package com.example.risingtest.src.main.store.productDetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemProductDetailRvInfoBinding

class ProductInformationRvAdapter: RecyclerView.Adapter<ProductInformationRvAdapter.ViewHolder>() {
    var list = mutableListOf<Int>()

    inner class ViewHolder(val binding: ItemProductDetailRvInfoBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Int){
            binding.ivImage.setImageResource(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductDetailRvInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductInformationRvAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListOfView(nList: MutableList<Int>){
        list = nList
    }

}