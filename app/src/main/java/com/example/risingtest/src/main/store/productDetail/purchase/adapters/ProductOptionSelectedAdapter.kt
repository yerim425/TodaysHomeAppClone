package com.example.risingtest.src.main.store.productDetail.purchase.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemProductOptionSelectedBinding
import com.example.risingtest.src.main.store.productDetail.purchase.datas.ProductOptionSelectedItemData

class ProductOptionSelectedAdapter: RecyclerView.Adapter<ProductOptionSelectedAdapter.ViewHolder>() {

    var list = mutableListOf<ProductOptionSelectedItemData>()

    inner class ViewHolder(val binding: ItemProductOptionSelectedBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProductOptionSelectedItemData){
            binding.tvOptionTitle.text = item.title
            binding.tvProductOptionAmount.text = item.price
        }

        init{
            binding.ivProductDelete.setOnClickListener {
                list.removeAt(adapterPosition)
                notifyDataSetChanged()
            }

            binding.ivNumPlus.setOnClickListener {
                var num = binding.tvProductNum.text.toString().toInt()
                num++
                binding.tvProductNum.text = num.toString()
            }
            binding.ivNumMinus.setOnClickListener {
                var num = binding.tvProductNum.text.toString().toInt()
                if(num > 1){
                    num--
                    binding.tvProductNum.text = num.toString()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductOptionSelectedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addProduct(item: ProductOptionSelectedItemData){
        list.add(item)
        notifyItemInserted(list.size-1)
        //notifyDataSetChanged()
    }
}