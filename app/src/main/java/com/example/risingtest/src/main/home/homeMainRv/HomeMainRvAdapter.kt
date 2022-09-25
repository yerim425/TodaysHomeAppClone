package com.example.risingtest.src.main.home.homeMainRv

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemRvHomeMainBinding
import com.example.risingtest.src.main.home.homeMainRv.homeSecondRv.HomePostRvGridAdapter

class HomeMainRvAdapter(val context: Context): RecyclerView.Adapter<HomeMainRvAdapter.ViewHolder>() {

    var list = mutableListOf<HomeMainRvItemData>()

    inner class ViewHolder(val binding: ItemRvHomeMainBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeMainRvItemData) {

            binding.tvTitle.setText(item.title)
            if (item.showDetail) {
                binding.ivDetail.visibility = View.VISIBLE
            } else binding.ivDetail.visibility = View.INVISIBLE


            if (item.viewType == "G2") { // GridLayout
                Log.d("HomeMainRvAdapter", item.postList.toString())
                binding.rvHomePost.layoutManager = GridLayoutManager(context, 2)
                binding.rvHomePost.adapter =
                    item.postList?.let { HomePostRvGridAdapter(it, context) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRvHomeMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<HomeMainRvItemData>){
        list = nList
    }

}