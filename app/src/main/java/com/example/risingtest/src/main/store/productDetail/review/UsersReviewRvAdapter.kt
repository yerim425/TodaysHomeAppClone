package com.example.risingtest.src.main.store.productDetail.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemProductDetailRvUsersReviewBinding
import com.example.risingtest.src.main.store.productDetail.models.productDetail.Review
import java.text.SimpleDateFormat
import java.util.*

class UsersReviewRvAdapter: RecyclerView.Adapter<UsersReviewRvAdapter.ViewHolder>() {

    var list = listOf<Review>()

    inner class ViewHolder(val binding: ItemProductDetailRvUsersReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Review){
            binding.tvUsersNickname.text = item.writerName
            binding.ratingBarUsersRating.rating = item.score.toFloat()
            binding.tvReviewCreatedAt.text = SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().time)
            binding.tvBoughtProduct.text = item.productName
            Glide.with(binding.ivUsersPhoto)
                .load(item.reviewPhotoUrl)
                .placeholder(R.drawable.shape_gray2_view_rounded_5)
                .error(R.drawable.shape_gray2_view_rounded_5)
                .fallback(R.drawable.shape_gray2_view_rounded_5)
                .override(200, 200)
                .centerCrop()
                .apply(RequestOptions.centerCropTransform())
                .into(binding.ivUsersPhoto)
            binding.tvUserReviewText.text = item.content
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
    fun getListFromView(nList: List<Review>){
        list = nList
    }

    fun getTodayDate(): String{
        return SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().time)
    }
}