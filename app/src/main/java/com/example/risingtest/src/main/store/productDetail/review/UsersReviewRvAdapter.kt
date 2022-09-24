package com.example.risingtest.src.main.store.productDetail.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemProductDetailRvUsersReviewBinding

class UsersReviewRvAdapter: RecyclerView.Adapter<UsersReviewRvAdapter.ViewHolder>() {

    var list = mutableListOf<UsersReviewItemData>()

    inner class ViewHolder(val binding: ItemProductDetailRvUsersReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: UsersReviewItemData){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductDetailRvUsersReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun getListFromView(nList: MutableList<UsersReviewItemData>){
        list = nList
    }
}