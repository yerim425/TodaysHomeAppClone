package com.example.risingtest.src.main.store.productDetail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemProductDetailCategoryBinding

class ProductDetailCategoryRvAdapter: RecyclerView.Adapter<ProductDetailCategoryRvAdapter.ViewHolder>() {

    var list = mutableListOf<String>()

    inner class ViewHolder(val binding: ItemProductDetailCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: String){

            binding.tvCategory.text = category

            if(adapterPosition == list.size-1){
                binding.ivArrowRight.visibility = View.GONE
            }else binding.ivArrowRight.visibility = View.VISIBLE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductDetailCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<String>){
        list = nList
        notifyDataSetChanged()
    }

}