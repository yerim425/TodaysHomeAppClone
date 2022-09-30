package com.example.risingtest.src.main.store.storeMainRv.storeProductRv

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingtest.databinding.ItemRvProductBaseBinding
import com.example.risingtest.src.main.store.StoreFragment
import com.example.risingtest.src.main.store.models.storePage.StorePageProduct
import com.example.risingtest.src.main.store.productDetail.ProductDetailActivity
import java.text.DecimalFormat
import java.util.*
import com.example.risingtest.src.main.store.StoreFragmentService
import kotlin.math.roundToInt

class StoreProductRvLinearAdapter(
    var prodList: MutableList<StorePageProduct>, val context: Context, val fragment: StoreFragment, val userIdx: Int)
    : RecyclerView.Adapter<StoreProductRvLinearAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRvProductBaseBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: StorePageProduct){

            // 제품 이름
            binding.tvProdTitle.text = item.name

            // 제품 섬네일
            Glide.with(binding.ivProdImg)
                .load(item.thumbnailUrl)
                .placeholder(R.drawable.shape_gray2_view_rounded_5)
                .error(R.drawable.shape_gray2_view_rounded_5)
                .fallback(R.drawable.shape_gray2_view_rounded_5)
                .override(380  , 380)
                .centerCrop()
                .apply(RequestOptions.centerCropTransform())
                .into(binding.ivProdImg)

            // 제품 브랜드
            binding.tvProdBrand.text = item.brandName

            // 제품 할인율, 할인된 가격
            var discountedPrice = item.discountedPrice // 할인할 가격
            var originalPrice = item.originalPrice
            if(discountedPrice == 0){
                binding.tvProdSalePercentage.visibility = View.GONE
                binding.tvProdPrice.text = getDecimalFormat(originalPrice)
            }else{
                binding.tvProdSalePercentage.visibility = View.VISIBLE
                var discountedPercentage = getdiscountPercentage(originalPrice, discountedPrice)
                binding.tvProdSalePercentage.text = discountedPercentage.toString() + "%"
                binding.tvProdPrice.text = getDecimalFormat(originalPrice-discountedPrice)
            }
            // 제품 평점
            var score = ((item.totalScore * 10.0).roundToInt() / 10.0)
            binding.tvProdRating.text = score.toString()

            // 제품 리뷰 개수
            binding.tvProdReviewNum.text = getDecimalFormat(item.numReviews)


            if(item.isTodaysDeal == true){
                binding.tvProdBrand.setTextColor(getColor(context, R.color.gray_text))
                binding.tvProdReview.typeface = Typeface.DEFAULT
                binding.tvProdReviewNum.typeface = Typeface.DEFAULT
                binding.tvProdRemainTime.visibility = View.VISIBLE
                val remainDate = getRemainDate(item.eventDeadline)
                binding.tvProdRemainTime.text = remainDate + "일 남음"
                item.remainDate = remainDate
            }else{
                binding.tvProdRemainTime.visibility = View.GONE
            }

            binding.cbScrap.isChecked = item.isScrabbed
            // 서버 수정되면 제거하기
            var scrapProduct = "scrapProduct"+item.productId.toString()
            binding.cbScrap.isChecked = sSharedPreferences.getString(scrapProduct, "false").toBoolean()


            binding.cbScrap.setOnClickListener {
                var editor = sSharedPreferences.edit()

                if(binding.cbScrap.isChecked){
                    StoreFragmentService(fragment).tryPostProductScrap(userIdx, item.productId)

                    editor.putString(scrapProduct, "true")
                }else{
                    StoreFragmentService(fragment).tryPatchProductScrapCancel(userIdx, item.productId)

                    editor.putString(scrapProduct, "false")
                }
                editor.commit()
                fragment.storeMainAdapter.notifyDataSetChanged()
            }


            binding.layoutProduct.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("newActivity", R.drawable.anim_slide_in_right.toString())
                intent.putExtra("preActivity", R.drawable.anim_slide_out_left.toString())
                intent.putExtra("productId", item.productId)
                intent.putExtra("userIdx", userIdx)

                if(item.remainDate != null){
                    intent.putExtra("remainDate", item.remainDate)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRvProductBaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(prodList[position])
    }

    override fun getItemCount(): Int {
        return prodList.size
    }


    fun getDecimalFormat(number: Int): String {
        val deciaml = DecimalFormat("#,###")
        return deciaml.format(number)
    }


    fun getdiscountPercentage(originalPrice: Int, discountedPrice: Int): Int{
        return (discountedPrice.toDouble()/originalPrice.toDouble()*100.0).toInt()

    }

    fun getRemainDate(deadlineDate: String): String{

        val todayCal = Calendar.getInstance()
        todayCal.add(Calendar.DAY_OF_YEAR, 0)
        val todayYear = todayCal.get(Calendar.YEAR)
        val todayMonth = (todayCal.get(Calendar.MONTH) + 1)
        val todayDay = todayCal.get(Calendar.DATE)
        Log.d("getDate", todayYear.toString()+ " " + todayMonth.toString()+ " " + todayDay.toString())
        val beginDay = Calendar.getInstance().apply {
            set(Calendar.YEAR, todayYear)
            set(Calendar.MONTH, todayMonth)
            set(Calendar.DAY_OF_MONTH, todayDay)
        }.timeInMillis

        val deadlineYear = deadlineDate.substring(0 until 4).toInt()
        val deadlineMonth = deadlineDate.substring(5 until 7).toInt()
        val deadlineDay = deadlineDate.substring(8 until 10).toInt()
        Log.d("getDate", deadlineYear.toString()+ " " + deadlineMonth.toString()+ " " + deadlineDay.toString())

        val lastDay = Calendar.getInstance().apply {
            set(Calendar.YEAR, deadlineYear)
            set(Calendar.MONTH, deadlineMonth)
            set(Calendar.DAY_OF_MONTH, deadlineDay)
        }.timeInMillis

        val result = getIgnoredTimeDays(lastDay) - getIgnoredTimeDays(beginDay)
        return (result/(24*60*60*1000)).toString()
    }
    fun getIgnoredTimeDays(time: Long):Long{
        return Calendar.getInstance().apply {
            timeInMillis = time

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}