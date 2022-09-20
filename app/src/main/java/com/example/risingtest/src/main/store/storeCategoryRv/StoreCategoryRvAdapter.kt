package com.example.risingtest.src.main.store.storeCategoryRv

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemRvStoreCategoryBinding

class StoreCategoryRvAdapter(val context: Context): RecyclerView.Adapter<StoreCategoryRvAdapter.ViewHolder>() {

    var list = mutableListOf<StoreCategoryRvItemData>()

    inner class ViewHolder(val binding: ItemRvStoreCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: StoreCategoryRvItemData){
            binding.ivItem.setImageResource(item.icon)
            binding.tvItem.setText(item.text)

            binding.laytoutItem.background = getDrawable(context, R.drawable.shape_store_category_gray)
            binding.tvItem.setTextColor(Color.BLACK)

            if(context.getString(item.text) == context.getString(R.string.category)){
                binding.laytoutItem.background = getDrawable(context, R.drawable.shape_store_category_blue)
                binding.tvItem.setTextColor(getColor(context, R.color.colorAccent))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRvStoreCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<StoreCategoryRvItemData>){
        list = nList
    }
}