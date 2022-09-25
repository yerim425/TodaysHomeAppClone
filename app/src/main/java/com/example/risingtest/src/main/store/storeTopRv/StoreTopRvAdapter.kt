package com.example.risingtest.src.main.store.storeTopRv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemRvStoreTopBinding

class StoreTopRvAdapter: RecyclerView.Adapter<StoreTopRvAdapter.ViewHolder>() {

    var list = mutableListOf<StoreTopRvItemData>()

    inner class ViewHolder(val binding: ItemRvStoreTopBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: StoreTopRvItemData){
            binding.ivItem.setImageResource(item.icon)
            binding.tvItem.setText(item.text)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRvStoreTopBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<StoreTopRvItemData>){
        list = nList
    }

}