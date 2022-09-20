package com.example.risingtest.src.login.byEmail.signUpByEmail

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemRvTermsAndConditionsBinding

class EmailSignUpAdapter(val context: Context): RecyclerView.Adapter<EmailSignUpAdapter.ViewHolder>() {

    var list = mutableListOf<EmailSignUpItemData>()
    var firstBind = true

    inner class ViewHolder(var binding: ItemRvTermsAndConditionsBinding): RecyclerView.ViewHolder(binding.root){
        init{


        }

        fun bind(item: EmailSignUpItemData){

            binding.cbTermsAndConditions.isChecked = item.isChecked

            if(firstBind){
                binding.tvTermsAndConditions.text = item.text
                var text = binding.tvTermsAndConditions.text
                if(item.isEssential){
                    var word = "(필수)"
                    var start = text.indexOf(word)
                    var end = start + word.length
                    val builder = SpannableStringBuilder(text)
                    builder.setSpan(RelativeSizeSpan(0.8f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    builder.setSpan(ForegroundColorSpan(getColor(context, R.color.signup_blue_text)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.tvTermsAndConditions.text = builder
                }else{
                    var word = "(선택)"
                    var start = text.indexOf(word)
                    var end = start + word.length
                    val builder = SpannableStringBuilder(text)
                    builder.setSpan(RelativeSizeSpan(0.8f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    builder.setSpan(ForegroundColorSpan(getColor(context, R.color.login_signup_gray_text_1)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.tvTermsAndConditions.text = builder
                }
                if(item.arrow){
                    binding.ivArrowRight.visibility = View.VISIBLE
                }else binding.ivArrowRight.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailSignUpAdapter.ViewHolder {
        return ViewHolder(ItemRvTermsAndConditionsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EmailSignUpAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<EmailSignUpItemData>){
        list = nList
    }

}