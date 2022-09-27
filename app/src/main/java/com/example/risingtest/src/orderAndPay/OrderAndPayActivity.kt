package com.example.risingtest.src.orderAndPay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.risingtest.R
import com.example.risingtest.config.BaseActivity
import com.example.risingtest.databinding.ActivityOrderAndPayBinding
import com.example.risingtest.src.orderAndPay.orderResults.OrderResultActivity
import com.example.risingtest.src.orderAndPay.paymentMeans.PaymentMeansAdapter
import com.example.risingtest.src.orderAndPay.paymentMeans.PaymentMeansItemData

class OrderAndPayActivity : BaseActivity<ActivityOrderAndPayBinding>(ActivityOrderAndPayBinding::inflate){

    lateinit var orderCancelDialog: OrderCancelDialog
    lateinit var paymentMeansAdapter: PaymentMeansAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.hasExtra("newActivity")){
            var newActivityAnim = intent.getStringExtra("newActivity")?.toInt()
            var preActivityAnim = intent.getStringExtra("preActivity")?.toInt()
            overridePendingTransition(newActivityAnim!!, preActivityAnim!!)
        }


        paymentMeansAdapter = PaymentMeansAdapter(this)
        paymentMeansAdapter.getListFromView(setPaymentMeansDataList())
        binding.rvPaymentMeans.layoutManager = GridLayoutManager(this, 4)
        binding.rvPaymentMeans.adapter = paymentMeansAdapter


        binding.cbAllAgreement.setOnClickListener {
            if(binding.cbAllAgreement.isChecked){
                binding.cbAgree1.isChecked = true
                binding.cbAgree2.isChecked = true
            }else{
                binding.cbAgree1.isChecked = false
                binding.cbAgree2.isChecked = false
            }
        }

        binding.layoutPaying.setOnClickListener {
            // 주문 내용 담아서 주문 요청
            // loading
            // onSuccess 시 주문 결과 화면 start
            startActivity(Intent(this, OrderResultActivity::class.java))
            finish()
        }


        binding.ivBack.setOnClickListener {
            finish()
        }

    }

    override fun onBackPressed() {
        orderCancelDialog = OrderCancelDialog(this, this)
        orderCancelDialog.show()
    }

    fun setPaymentMeansDataList(): MutableList<PaymentMeansItemData>{
        var list = mutableListOf<PaymentMeansItemData>()
        list.add(PaymentMeansItemData(getString(R.string.card), R.drawable.img_payment_means_card, null, null))
        list.add(PaymentMeansItemData(
            getString(R.string.deposit_without_bankbook), R.drawable.img_payment_means_deposit_without_bankbook,
            null, R.drawable.img_payment_means_deposit_without_bankbook_benefit))
        list.add(PaymentMeansItemData(
            getString(R.string.kakao_pay), R.drawable.img_payment_means_kakao,
                getString(R.string.benefit_of_kakao_pay), R.drawable.img_payment_means_kakao_benefit))
        list.add(PaymentMeansItemData(
            getString(R.string.toss), R.drawable.img_payment_means_toss,
            getString(R.string.benefit_of_toss), R.drawable.img_payment_means_toss_benefit))
        list.add(PaymentMeansItemData(
            getString(R.string.naver_pay), R.drawable.img_payment_means_naver,
            getString(R.string.benefit_of_naver_pay), R.drawable.img_payment_means_naver_benefit))
        list.add(PaymentMeansItemData(
            getString(R.string.payco), R.drawable.img_payment_means_payco,
            getString(R.string.benefit_of_payco), R.drawable.img_payment_means_payco_benefit))
        list.add(PaymentMeansItemData(
            getString(R.string.phone), R.drawable.img_payment_means_phone,null, null))
        return list
    }
}

