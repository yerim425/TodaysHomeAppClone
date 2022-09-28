package com.example.risingtest.src.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemViewpagerBaseBinding

class HomeViewPagerAdapter: RecyclerView.Adapter<HomeViewPagerAdapter.ViewHolder>() {
    var pageList = mutableListOf<Int>()

    inner class ViewHolder(val binding: ItemViewpagerBaseBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Int){
            binding.ivViewpager.setImageResource(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewpagerBaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewPagerAdapter.ViewHolder, position: Int) {
        holder.bind(pageList[position%pageList.size])

    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    fun getListOfView(nList: MutableList<Int>){
        pageList = nList
    }




}