package com.example.risingtest.src.main.upload

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemUploadTypeBinding

class UploadTypeAdapter: RecyclerView.Adapter<UploadTypeAdapter.ViewHolder>() {

    var list = mutableListOf<UploadTypeItemData>()

    inner class ViewHolder(val binding: ItemUploadTypeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: UploadTypeItemData){
            binding.imItemUploadType.setImageResource(item.image)
            binding.tvUploadTypeTitle.text = item.title
            if(item.benefit != null){
                binding.tvUploadTypeBenefit.text = item.benefit
                binding.tvUploadTypeBenefit.visibility = View.VISIBLE
            }else{
                binding.tvUploadTypeBenefit.visibility = View.GONE
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUploadTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<UploadTypeItemData>){
        list = nList
    }
}