package com.example.risingtest.src.scrap.fragments.scrapProduct

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentScrapProductBinding
import com.example.risingtest.databinding.ItemRvProductBaseBinding
import com.example.risingtest.src.main.store.models.storePage.StorePageProduct
import com.example.risingtest.src.scrap.ScrapActivity
import com.example.risingtest.src.scrap.fragments.scrapProduct.scrapedProductItem.ScrabBanner
import com.example.risingtest.src.scrap.fragments.scrapProduct.scrapedProductItem.ScrabItem
import com.example.risingtest.src.scrap.fragments.scrapProduct.scrapedProductItem.ScrapedProductItemResponse
import com.google.android.material.tabs.TabLayoutMediator
import java.text.DecimalFormat


class ScrapedProductFragment(val activity: ScrapActivity) : BaseFragment<FragmentScrapProductBinding>(
    FragmentScrapProductBinding::bind, R.layout.fragment_scrap_product), ScrapedProductFragmentInterface {

    var editLayoutVisible = false
    lateinit var productScrapRvAdapter: ProductScrapAdapter
    var showCheckBox = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        ScrapedProductService(this).tryGetScrapedProductItem(
            sSharedPreferences.getString("userIdx", "-1")!!.toInt()
        )


        binding.tvEdit.setOnClickListener {
            editLayoutVisible = true
            binding.layoutEdit.visibility = View.INVISIBLE
            binding.layoutMoveDelete.visibility = View.VISIBLE

            showCheckBox = true
            productScrapRvAdapter.notifyDataSetChanged()

        }
        binding.tvCancel.setOnClickListener {
            binding.layoutMoveDelete.visibility = View.INVISIBLE
            binding.layoutEdit.visibility = View.VISIBLE

            showCheckBox = false
            productScrapRvAdapter.notifyDataSetChanged()

        }


        productScrapRvAdapter = ProductScrapAdapter()
        binding.rvScrap.layoutManager = GridLayoutManager(context, 2)
        binding.rvScrap.adapter = productScrapRvAdapter
    }

    inner class ProductScrapAdapter: RecyclerView.Adapter<ProductScrapAdapter.ViewHolder>(){

        var list = mutableListOf<ScrabItem>()

        inner class ViewHolder(val binding: ItemRvProductBaseBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(item: ScrabItem){

                Glide.with(binding.ivProdImg)
                    .load(item.thumbnailUrl) // item.img
                    .override(470, 470)
                    .apply(RequestOptions.centerCropTransform())
                    .into(binding.ivProdImg)
                binding.tvProdBrand.text = item.brandName
                binding.tvProdTitle.text = item.productName
                binding.tvProdSalePercentage.text =
                    getdiscountPercentage(item.originalPrice, item.discountedPrice).toString() + "%"
                binding.tvProdPrice.text = getDecimalFormat(item.originalPrice-item.discountedPrice)
                binding.tvProdRating.text = item.totalScore.toString()
                binding.tvProdReviewNum.text = item.numReviews.toString()
                binding.cbScrap.isChecked = true


                if(showCheckBox){
                    binding.cbEdit.visibility = View.VISIBLE
                }else binding.cbEdit.visibility = View.INVISIBLE


            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemRvProductBaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun getListFromView(nList: MutableList<ScrabItem>){
            list = nList
            notifyDataSetChanged()
        }

    }


    override fun onGetScrapedProductItemSuccess(response: ScrapedProductItemResponse) {
        dismissLoadingDialog()


        var list = mutableListOf<ScrabItem>()
        for(item in response.result.scrabItems){
            list.add(item)
        }
        productScrapRvAdapter.getListFromView(list)




    }

    override fun onGetScrapedProductItemFailure(message: String) {
        dismissLoadingDialog()

        showCustomToast("스크랩한 상품 조회 요청 실패")
        Log.d("scrapedProductFragment", message)
    }

    fun getDecimalFormat(number: Int): String {
        val deciaml = DecimalFormat("#,###")
        return deciaml.format(number)
    }

    fun getdiscountPercentage(originalPrice: Int, discountedPrice: Int): Int{
        return (discountedPrice.toDouble()/originalPrice.toDouble()*100.0).toInt()
    }

}