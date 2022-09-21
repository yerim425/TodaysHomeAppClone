package com.example.risingtest.src.scrap.fragments

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
import com.example.risingtest.src.scrap.datas.ScrapAllItemData


class ScrapAllFragment : BaseFragment<FragmentScrapAllBinding>(FragmentScrapAllBinding::bind, R.layout.fragment_scrap_all){

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

        allScrapRvAdapter = AllScrapRvAdapter()
        allScrapRvAdapter.getListFromView(setList())
        binding.rvScrap.layoutManager = GridLayoutManager(context, 2)
        binding.rvScrap.adapter = allScrapRvAdapter





    }

    fun setList(): MutableList<ScrapAllItemData>{

        var list = mutableListOf<ScrapAllItemData>()
        list.add(ScrapAllItemData(R.drawable.img_prod1_1, getString(R.string.product), false))
        list.add(ScrapAllItemData(R.drawable.img_prod1_2, getString(R.string.product), false))
        list.add(ScrapAllItemData(R.drawable.img_prod1_3, getString(R.string.product), false))
        list.add(ScrapAllItemData(R.drawable.img_prod2, getString(R.string.product), false))
        list.add(ScrapAllItemData(R.drawable.img_prod3, getString(R.string.product), false))
        list.add(ScrapAllItemData(R.drawable.img_prod3, getString(R.string.product), false))
        list.add(ScrapAllItemData(R.drawable.img_prod3, getString(R.string.product), false))
        list.add(ScrapAllItemData(R.drawable.img_prod3, getString(R.string.product), false))
        list.add(ScrapAllItemData(R.drawable.img_prod3, getString(R.string.product), false))
        list.add(ScrapAllItemData(R.drawable.img_prod3, getString(R.string.product), false))
        return list
    }




    inner class AllScrapRvAdapter: RecyclerView.Adapter<AllScrapRvAdapter.ViewHolder>(){

        var list = mutableListOf<ScrapAllItemData>()

        inner class ViewHolder(val binding: ItemScrapProductSimpleBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(item: ScrapAllItemData){

                binding.ivProdScrap.setImageResource(item.img)

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

        fun getListFromView(nList: MutableList<ScrapAllItemData>){
            list = nList
        }

    }
}