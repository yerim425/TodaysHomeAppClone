package com.example.risingtest.src.main.store.productDetail.adapters.productDetailRvMainAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemProductDetailRvMainBinding

class ProductDetailRvMainAdapter: RecyclerView.Adapter<ProductDetailRvMainAdapter.ViewHolder>() {

    var list = mutableListOf<ProductDetailRvMainItemData>()

    inner class ViewHolder(val binding: ItemProductDetailRvMainBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProductDetailRvMainItemData){

            binding.tvTitle.text = item.title

            if(item.num != null){
                binding.tvNum.text = item.num.toString()
                binding.tvNum.visibility = View.VISIBLE
            }else binding.tvNum.visibility = View.GONE

            if(item.isDetail){
                binding.ivDetail.visibility = View.VISIBLE
            }else binding.ivDetail.visibility = View.INVISIBLE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductDetailRvMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
       return list.size
    }
    fun getListFromView(nList: MutableList<ProductDetailRvMainItemData>){
        list = nList
    }
}