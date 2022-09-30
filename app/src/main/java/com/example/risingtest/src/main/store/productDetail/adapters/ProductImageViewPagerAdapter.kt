package com.example.risingtest.src.main.store.productDetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemViewpagerBaseBinding

class ProductImageViewPagerAdapter: RecyclerView.Adapter<ProductImageViewPagerAdapter.ViewHolder>() {
    var pageList = listOf<String>()

    inner class ViewHolder(val binding: ItemViewpagerBaseBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(imageUrl: String){
            Glide.with(binding.ivViewpager)
                .load(imageUrl)
                .placeholder(R.drawable.shape_gray2_view_rounded_5)
                .error(R.drawable.shape_gray2_view_rounded_5)
                .fallback(R.drawable.shape_gray2_view_rounded_5)
                .override(1280, 1280)
                .centerCrop()
                .apply(RequestOptions.centerCropTransform())
                .into(binding.ivViewpager)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewpagerBaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductImageViewPagerAdapter.ViewHolder, position: Int) {
        holder.bind(pageList[position])
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    fun getListOfView(nList: List<String>){
        pageList = nList
        notifyDataSetChanged()
    }

}