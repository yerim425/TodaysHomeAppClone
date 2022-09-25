package com.example.risingtest.src.main.home.homeMainRv.homeSecondRv

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemRvHomePopluarPostBinding

class HomePostRvGridAdapter(var postList: MutableList<HomePopularPostItemData>, val context: Context)
    : RecyclerView.Adapter<HomePostRvGridAdapter.ViewHolder>() {

    //var list = mutableListOf<HomePopularPostItemData>()

    inner class ViewHolder(val binding: ItemRvHomePopluarPostBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: HomePopularPostItemData){

            binding.ivPostImg.setImageResource(item.image)
            binding.cbScrap.isChecked = false
            binding.tvPostTitle.text = item.titleHighlight + " " + item.titleNormal
            val builder = SpannableStringBuilder(binding.tvPostTitle.text)
            builder.setSpan(ForegroundColorSpan(getColor(context, R.color.colorAccent)), 0, item.titleHighlight.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(StyleSpan(Typeface.BOLD), 0, item.titleHighlight.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvPostTitle.text = builder

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRvHomePopluarPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}