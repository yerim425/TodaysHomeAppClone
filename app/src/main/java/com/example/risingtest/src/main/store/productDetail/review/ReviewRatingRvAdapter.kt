package com.example.risingtest.src.main.store.productDetail.review

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemProductDetailRvReviewRatingProgressbarBinding

class ReviewRatingRvAdapter(val context: Context): RecyclerView.Adapter<ReviewRatingRvAdapter.ViewHolder>() {

    var list = mutableListOf<ReviewRatingItemData>()

    inner class ViewHolder(val binding: ItemProductDetailRvReviewRatingProgressbarBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ReviewRatingItemData){
            binding.tvScore.text = item.score

            var amount = item.thisNum * (item.totalNum*0.01)
            binding.progressBar.progress = amount.toInt()
            Log.d("ReviewRatingRvAdapter", amount.toString())

            binding.tvNum.text = item.thisNum.toString()

            if(item.most){
                binding.tvScore.setTextColor(getColor(context, R.color.colorAccent))
                binding.tvScore.setTypeface(Typeface.DEFAULT_BOLD)
                binding.tvNum.setTextColor(Color.BLACK)
                binding.tvNum.setTypeface(Typeface.DEFAULT_BOLD)
            }else{
                binding.tvScore.setTextColor(getColor(context, R.color.gray_text_dark))
                binding.tvScore.setTypeface(Typeface.DEFAULT)
                binding.tvNum.setTextColor(getColor(context, R.color.gray_text_dark))
                binding.tvNum.setTypeface(Typeface.DEFAULT)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductDetailRvReviewRatingProgressbarBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<ReviewRatingItemData>){
        list = nList
    }
}