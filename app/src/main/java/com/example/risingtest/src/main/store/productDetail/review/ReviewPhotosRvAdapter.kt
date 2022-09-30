package com.example.risingtest.src.main.store.productDetail.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemProductDetailRvReviewPhotosBinding

class ReviewPhotosRvAdapter: RecyclerView.Adapter<ReviewPhotosRvAdapter.ViewHolder>() {

    var list = mutableListOf<String>()

    inner class ViewHolder(val binding: ItemProductDetailRvReviewPhotosBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(imageUrl: String){

            Glide.with(binding.image)
                .load(imageUrl)
                .placeholder(R.drawable.shape_gray2_view_rounded_5)
                .error(R.drawable.shape_gray2_view_rounded_5)
                .fallback(R.drawable.shape_gray2_view_rounded_5)
                .override(250, 250)
                .centerCrop()
                .apply(RequestOptions.centerCropTransform())
                .into(binding.image)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductDetailRvReviewPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<String>){
        list = nList
    }

}