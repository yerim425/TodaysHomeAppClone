package com.example.risingtest.src.orderAndPay.paymentMeans

import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.risingtest.R
import com.example.risingtest.databinding.ItemPaymentMeansBinding
import com.example.risingtest.src.orderAndPay.OrderAndPayActivity

class PaymentMeansAdapter(val activity: OrderAndPayActivity): RecyclerView.Adapter<PaymentMeansAdapter.ViewHolder>() {

    var list = mutableListOf<PaymentMeansItemData>()
    var bindingList = mutableListOf<ItemPaymentMeansBinding>()
    var benefitDetailList = arrayListOf<Int>(
        R.drawable.img_payment_means_deposit_without_bankbook_benefit, R.drawable.img_payment_means_kakao_benefit,
        R.drawable.img_payment_means_toss_benefit, R.drawable.img_payment_means_naver_benefit,
        R.drawable.img_payment_means_payco_benefit
    )

    lateinit var selectedPaymentMethod: String

    inner class ViewHolder(val binding: ItemPaymentMeansBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: PaymentMeansItemData){
            binding.tvPaymentMeans.text = item.title
            binding.imageView.setImageResource(item.Image)
            if(item.benefit != null){
                binding.tvBenefit.text = item.benefit
                binding.tvBenefit.visibility = View.VISIBLE
            }else {
                binding.tvBenefit.visibility = View.INVISIBLE
            }

            if(item.title.equals("카드")){
                binding.layoutPaymentMeans.background = getDrawable(activity, R.drawable.shape_blue_view_edge)
                binding.tvPaymentMeans.typeface = Typeface.DEFAULT_BOLD
                activity.findViewById<ConstraintLayout>(R.id.layout_pay_by_card).visibility = View.VISIBLE
                activity.findViewById<ConstraintLayout>(R.id.layout_pay_by_others).visibility = View.GONE
            }

        }
        init{
            bindingList.add(binding)
            binding.layoutPaymentMeans.setOnClickListener {
                for(i in 0 until bindingList.size){
                    bindingList[i].layoutPaymentMeans.background = getDrawable(activity, R.drawable.shape_gray_edge)
                    bindingList[i].tvPaymentMeans.typeface = Typeface.DEFAULT
                }
                binding.layoutPaymentMeans.background = getDrawable(activity, R.drawable.shape_blue_view_edge)
                binding.tvPaymentMeans.typeface = Typeface.DEFAULT_BOLD

                selectedPaymentMethod = binding.tvPaymentMeans.text.toString()

                when(binding.tvPaymentMeans.text){
                    "카드" -> {
                        activity.findViewById<ConstraintLayout>(R.id.layout_pay_by_card).visibility = View.VISIBLE
                        activity.findViewById<ConstraintLayout>(R.id.layout_pay_by_others).visibility = View.GONE}
                    "무통장입금" -> {setBenefitLayout(0)}
                    "카카오페이" -> {setBenefitLayout(1)}
                    "토스" -> {setBenefitLayout(2)}
                    "네이버페이" -> {setBenefitLayout(3)}
                    "페이코" -> {setBenefitLayout(4)}
                    "핸드폰" -> {
                        activity.findViewById<ConstraintLayout>(R.id.layout_pay_by_card).visibility = View.GONE
                        activity.findViewById<ConstraintLayout>(R.id.layout_pay_by_others).visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPaymentMeansBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getListFromView(nList: MutableList<PaymentMeansItemData>){
        list = nList
    }

    fun setBenefitLayout(idx: Int){
        activity.findViewById<ConstraintLayout>(R.id.layout_pay_by_card).visibility = View.GONE
        activity.findViewById<ConstraintLayout>(R.id.layout_pay_by_others).visibility = View.VISIBLE
        activity.findViewById<ImageView>(R.id.iv_pay_by_others).setImageResource(benefitDetailList[idx])
    }

    fun getSelectedPayment(): String{
        return selectedPaymentMethod
    }

}