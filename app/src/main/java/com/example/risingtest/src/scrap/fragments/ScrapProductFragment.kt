package com.example.risingtest.src.scrap.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentScrapProductBinding
import com.example.risingtest.databinding.ItemRvProductBaseBinding
import com.example.risingtest.databinding.ItemScrapProductSimpleBinding
import com.example.risingtest.src.main.store.storeMainRv.storeSecondRv.ProductItemData
import com.example.risingtest.src.scrap.datas.ScrapAllItemData


class ScrapProductFragment : BaseFragment<FragmentScrapProductBinding>(
    FragmentScrapProductBinding::bind, R.layout.fragment_scrap_product) {

    var editLayoutVisible = false
    lateinit var productScrapRvAdapter: ProductScrapAdapter
    var showCheckBox = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
        productScrapRvAdapter.getListFromView(setList())
        binding.rvScrap.layoutManager = GridLayoutManager(context, 2)
        binding.rvScrap.adapter = productScrapRvAdapter

    }


    fun setList(): MutableList<ProductItemData>{

        var list = mutableListOf<ProductItemData>()
        for(i in 0 until 10){
            list.add(ProductItemData())
        }
        return list
    }

    inner class ProductScrapAdapter: RecyclerView.Adapter<ProductScrapAdapter.ViewHolder>(){

        var list = mutableListOf<ProductItemData>()

        inner class ViewHolder(val binding: ItemRvProductBaseBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(item: ProductItemData){


                Glide.with(binding.ivProdImg)
                    .load(R.drawable.img_prod1_1) // item.img
                    .override(470, 470)
                    .apply(RequestOptions.centerCropTransform())
                    .into(binding.ivProdImg)


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

        fun getListFromView(nList: MutableList<ProductItemData>){
            list = nList
        }

    }

}