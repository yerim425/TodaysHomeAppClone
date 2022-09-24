package com.example.risingtest.src.main.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemViewpagerBaseBinding

class StoreViewPagerAdapter(): RecyclerView.Adapter<StoreViewPagerAdapter.ViewHolder>() {
    var pageList = mutableListOf<Int>()

    inner class ViewHolder(val binding: ItemViewpagerBaseBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Int){
            binding.ivViewpager.setImageResource(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewpagerBaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StoreViewPagerAdapter.ViewHolder, position: Int) {
        holder.bind(pageList[position%pageList.size])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    fun getListOfView(nList: MutableList<Int>){
        pageList = nList
    }




}