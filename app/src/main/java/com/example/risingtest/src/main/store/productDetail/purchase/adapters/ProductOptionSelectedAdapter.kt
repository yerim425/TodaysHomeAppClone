package com.example.risingtest.src.main.store.productDetail.purchase.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemProductOptionSelectedBinding
import com.example.risingtest.src.main.store.productDetail.models.productDetail.Option
import com.example.risingtest.src.main.store.productDetail.purchase.datas.ProductOptionSelectedItemData
import com.example.risingtest.src.main.store.productDetail.purchase.dialogs.ProductPurchaseDialog
import com.example.risingtest.src.main.store.productDetail.purchase.dialogs.productTotalPrice
import java.text.DecimalFormat

class ProductOptionSelectedAdapter(val dialog: ProductPurchaseDialog, val price: Int)
    : RecyclerView.Adapter<ProductOptionSelectedAdapter.ViewHolder>() {

    var list = mutableListOf<Option>()
    var optionTotalPriceList = mutableListOf<Int>()
    var productNumber = 1

    inner class ViewHolder(val binding: ItemProductOptionSelectedBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Option){
            binding.tvOptionTitle.text = item.optionName
            binding.tvProductOptionAmount.text = getDecimalFormat(price+item.optionPrice) +"원"
            binding.tvProductNum.text = "1"

            optionTotalPriceList.add((price+item.optionPrice))

            productTotalPrice += (price+item.optionPrice)
            dialog.setProductTotalPrice(productTotalPrice)


            binding.ivProductDelete.setOnClickListener {
                productTotalPrice -= optionTotalPriceList[adapterPosition]
                dialog.setProductTotalPrice(productTotalPrice)
                optionTotalPriceList.removeAt(adapterPosition)
                list.removeAt(adapterPosition)

                notifyDataSetChanged()
            }

            binding.ivNumPlus.setOnClickListener {
                var num = binding.tvProductNum.text.toString().toInt()
                num++
                binding.tvProductNum.text = num.toString()
                productNumber++
                optionTotalPriceList[adapterPosition] += (price+item.optionPrice)
                binding.tvProductOptionAmount.text = getDecimalFormat(optionTotalPriceList[adapterPosition]) + "원"

                productTotalPrice += (price+item.optionPrice)
                dialog.setProductTotalPrice(productTotalPrice)
            }
            binding.ivNumMinus.setOnClickListener {
                var num = binding.tvProductNum.text.toString().toInt()
                if(num > 1){
                    num--
                    binding.tvProductNum.text = num.toString()
                    productNumber--

                    optionTotalPriceList[adapterPosition] -= (price+item.optionPrice)
                    binding.tvProductOptionAmount.text = getDecimalFormat(optionTotalPriceList[adapterPosition])+ "원"

                    productTotalPrice -= (price+item.optionPrice)
                    dialog.setProductTotalPrice(productTotalPrice)
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

    fun addProduct(item: Option){
        list.add(item)
        notifyItemInserted(list.size-1)
        //notifyDataSetChanged()
    }

    fun getDecimalFormat(number: Int): String {
        val deciaml = DecimalFormat("#,###")
        return deciaml.format(number)
    }

    fun getOptionName(): String{
        return list[0].optionName
    }

    fun getProductNum(): Int{
        return productNumber
    }

    fun getOptionId(): Int{
        return list[0].productOptionId
    }

    fun getProductId(): Int{
        return list[0].productId
    }

}