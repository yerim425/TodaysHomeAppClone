package com.example.risingtest.src.main.store.productDetail.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.databinding.ItemProductDetailRvReviewPhotosBinding

class ReviewPhotosRvAdapter: RecyclerView.Adapter<ReviewPhotosRvAdapter.ViewHolder>() {

    var list = mutableListOf<Int>()

    inner class ViewHolder(val binding: ItemProductDetailRvReviewPhotosBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(image: Int){

            binding.image.setImageResource(image)


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

    fun getListFromView(nList: MutableList<Int>){
        list = nList
    }

}