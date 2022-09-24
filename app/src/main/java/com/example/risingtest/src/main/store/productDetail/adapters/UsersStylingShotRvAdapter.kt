package com.example.risingtest.src.main.store.productDetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemProductDetailRvUsersStylingShotBinding

class UsersStylingShotRvAdapter: RecyclerView.Adapter<UsersStylingShotRvAdapter.ViewHolder>() {

    var list = mutableListOf<Int>()

    inner class ViewHolder(val binding: ItemProductDetailRvUsersStylingShotBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Int){

            binding.image.setImageResource(image)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductDetailRvUsersStylingShotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<Int>){
        list = nList
    }

}