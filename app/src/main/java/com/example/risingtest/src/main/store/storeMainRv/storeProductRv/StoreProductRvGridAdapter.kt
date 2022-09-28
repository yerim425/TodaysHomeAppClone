package com.example.risingtest.src.main.store.storeMainRv.storeProductRv

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingtest.databinding.ItemRvProductBaseBinding
import com.example.risingtest.src.main.store.StoreFragment
import com.example.risingtest.src.main.store.StoreFragmentService
import com.example.risingtest.src.main.store.models.storePage.StorePageProduct
import com.example.risingtest.src.main.store.productDetail.ProductDetailActivity
import java.text.DecimalFormat

class StoreProductRvGridAdapter(
    var prodList: MutableList<StorePageProduct>, val context: Context, val fragment: StoreFragment, val userIdx: Int)
    : RecyclerView.Adapter<StoreProductRvGridAdapter.ViewHolder>() {

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
                .override(470, 470)
                .apply(RequestOptions.centerCropTransform())
                .into(binding.ivProdImg)

            // 제품 브랜드
            binding.tvProdBrand.text = item.brandName

            // 제품 할인율, 할인된 가격
            var discountedPrice = item.discountedPrice
            var originalPrice = item.originalPrice
            if(discountedPrice == 0){
                binding.tvProdSalePercentage.visibility = View.GONE
                binding.tvProdPrice.text = getDecimalFormat(originalPrice)
            }else{
                binding.tvProdSalePercentage.visibility = View.VISIBLE
                var discountedPercentage = (100/(originalPrice/discountedPrice))
                binding.tvProdSalePercentage.text = discountedPercentage.toString() + "%"
                binding.tvProdPrice.text = getDecimalFormat(discountedPrice)
            }

            // 제품 평점
            binding.tvProdRating.text = item.totalScore.toString()

            // 제품 리뷰 개수
            binding.tvProdReviewNum.text = getDecimalFormat(item.numReviews)

            binding.cbScrap.isChecked = item.isScrabbed

            var scrapProduct = "scrapProduct"+item.productId.toString()
            binding.cbScrap.isChecked = sSharedPreferences.getString(scrapProduct, "false").toBoolean()


            binding.cbScrap.setOnClickListener {
                var editor = sSharedPreferences.edit()

                if(binding.cbScrap.isChecked){
                    StoreFragmentService(fragment).tryPostProductScrap(userIdx, item.productId)

                    editor.putString(scrapProduct, "true")
                }else{
                    editor.putString(scrapProduct, "false")
                }
                editor.commit()
                fragment.storeMainAdapter.notifyDataSetChanged()
            }
        }

        init{
            binding.layoutProduct.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("newActivity", R.drawable.anim_slide_in_right.toString())
                intent.putExtra("preActivity", R.drawable.anim_slide_out_left.toString())
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
}