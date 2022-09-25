package com.example.risingtest.src.main.store.storeTopRv

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemRvHomeTopBinding

class HomeTopRvAdapter(val activity: Activity): RecyclerView.Adapter<HomeTopRvAdapter.ViewHolder>() {

    var list = mutableListOf<HomeTopRvItemData>()

    inner class ViewHolder(val binding: ItemRvHomeTopBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: HomeTopRvItemData){
            binding.ivItem.setImageResource(item.icon)
            binding.tvItem.setText(item.text)

            binding.layoutItem.setOnClickListener {
                if(item.icon == R.string.shopping){

                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRvHomeTopBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<HomeTopRvItemData>){
        list = nList
    }

}