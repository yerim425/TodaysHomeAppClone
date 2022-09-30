package com.example.risingtest.src.scrap.fragments.scrapAll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentScrapAllBinding
import com.example.risingtest.databinding.ItemScrapProductSimpleBinding
import com.example.risingtest.src.scrap.ScrapActivity
import com.example.risingtest.src.scrap.datas.ScrapAllItemData
import com.example.risingtest.src.scrap.fragments.scrapAll.scrapedAllItem.ScrabItem


class ScrapedAllFragment(val activity: ScrapActivity) : BaseFragment<FragmentScrapAllBinding>(
    FragmentScrapAllBinding::bind, R.layout.fragment_scrap_all){

    var editLayoutVisible = false
    lateinit var allScrapRvAdapter: AllScrapRvAdapter
    var showCheckBox = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvEdit.setOnClickListener {
            editLayoutVisible = true
            binding.layoutEdit.visibility = View.INVISIBLE
            binding.layoutMoveDelete.visibility = View.VISIBLE

            showCheckBox = true
            allScrapRvAdapter.notifyDataSetChanged()

        }
        binding.tvCancel.setOnClickListener {
            binding.layoutMoveDelete.visibility = View.INVISIBLE
            binding.layoutEdit.visibility = View.VISIBLE

            showCheckBox = false
            allScrapRvAdapter.notifyDataSetChanged()

        }


        if(activity.scrapedAllResult.scrabBanner.scrabTotalCount == 0){
            binding.tvNoContents.visibility=View.VISIBLE
        }else{
            binding.tvNoContents.visibility=View.INVISIBLE
            allScrapRvAdapter = AllScrapRvAdapter()
            allScrapRvAdapter.getListFromView(setList())
            binding.rvScrap.layoutManager = GridLayoutManager(context, 2)
            binding.rvScrap.adapter = allScrapRvAdapter
        }


    }

    fun setList(): MutableList<ScrabItem>{

        var list = mutableListOf<ScrabItem>()
        for(item in activity.scrapedAllResult.scrabItems){
            list.add(item)
        }
        return list

    }




    inner class AllScrapRvAdapter: RecyclerView.Adapter<AllScrapRvAdapter.ViewHolder>(){

        var list = mutableListOf<ScrabItem>()

        inner class ViewHolder(val binding: ItemScrapProductSimpleBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(item: ScrabItem){

                Glide.with(binding.ivProdScrap)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.shape_gray2_view_rounded_5)
                    .error(R.drawable.shape_gray2_view_rounded_5)
                    .fallback(R.drawable.shape_gray2_view_rounded_5)
                    .override(470  , 470)
                    .centerCrop()
                    .apply(RequestOptions.centerCropTransform())
                    .into(binding.ivProdScrap)

                when(item.type){
                    "Photo" -> {binding.tvProdCategory.text = getString(R.string.photo)}
                    "Product" -> {binding.tvProdCategory.text = getString(R.string.product)}
                }

                if(showCheckBox){
                    binding.cbEdit.visibility = View.VISIBLE
                }else binding.cbEdit.visibility = View.INVISIBLE


            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemScrapProductSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun getListFromView(nList: MutableList<ScrabItem>){
            list = nList
        }

    }
}