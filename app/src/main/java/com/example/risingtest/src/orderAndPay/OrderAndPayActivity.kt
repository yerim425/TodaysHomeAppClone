package com.example.risingtest.src.orderAndPay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingtest.config.BaseActivity
import com.example.risingtest.databinding.ActivityOrderAndPayBinding
import com.example.risingtest.src.login.byEmail.signUpByEmail.EmailSignUpService
import com.example.risingtest.src.login.byEmail.signUpByEmail.models.PostEmailSignUpRequest
import com.example.risingtest.src.orderAndPay.models.OrderAndPayResponse
import com.example.risingtest.src.orderAndPay.models.PostOrderAndPayRequest
import com.example.risingtest.src.orderAndPay.orderProduct.OrderProductAdapter
import com.example.risingtest.src.orderAndPay.orderProduct.OrderProductItemData
import com.example.risingtest.src.orderAndPay.orderResults.OrderResultActivity
import com.example.risingtest.src.orderAndPay.paymentMeans.PaymentMeansAdapter
import com.example.risingtest.src.orderAndPay.paymentMeans.PaymentMeansItemData
import java.text.DecimalFormat

class OrderAndPayActivity : BaseActivity<ActivityOrderAndPayBinding>(ActivityOrderAndPayBinding::inflate)
    , OrderAndPayActivityInterface{

    lateinit var orderCancelDialog: OrderCancelDialog
    lateinit var orderProductAdapter: OrderProductAdapter
    lateinit var paymentMeansAdapter: PaymentMeansAdapter
    lateinit var orderProduct: OrderProductItemData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.hasExtra("newActivity")){
            var newActivityAnim = intent.getStringExtra("newActivity")?.toInt()
            var preActivityAnim = intent.getStringExtra("preActivity")?.toInt()
            overridePendingTransition(newActivityAnim!!, preActivityAnim!!)
        }


        // 상품 상세 조회 화면에서 바로 구매
        if(intent.getStringExtra("isProductDetail") != null){
            orderProduct = OrderProductItemData(
                intent.getStringExtra("brandName")!!,
                intent.getStringExtra("imageUrl")!!,
                intent.getStringExtra("productName")!!,
                intent.getStringExtra("optionName")!!,
                intent.getIntExtra("productId", -1),
                intent.getIntExtra("optionId", -1),
                intent.getIntExtra("price", -1),
                intent.getIntExtra("productNum", -1)
            )



            orderProductAdapter = OrderProductAdapter()
            orderProductAdapter.getListFromView(setOrderProductList())
            binding.rvOrderProduct.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvOrderProduct.adapter = orderProductAdapter



            binding.tvTotalProductAmount.text = getDecimalFormat(orderProduct.price)+ "원"
            binding.tvFinalPaymentAmount.text = getDecimalFormat(orderProduct.price)+"원"
            var getPoints = (orderProduct.price.toDouble()*0.001).toInt()
            binding.tvAccumulatePoint.text = getPoints.toString() + "P"

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

        binding.tvPaying.text = getDecimalFormat(orderProduct.price) + "원"

        binding.layoutPaying.setOnClickListener {
            val postRequest = PostOrderAndPayRequest(
                buyerIdx = sSharedPreferences.getString("userIdx", null)!!.toInt(),
                productId = orderProduct.productId,
                productOptionId = orderProduct.optionId,
                price = orderProduct.price,
                buyerName = binding.tvBuyer.text.toString(),
                email = binding.edtUserEmail.text.toString() + "@" + binding.tvEmailSite.text,
                phoneNumber = "010-" + binding.edtUserCellPhoneSecond.text.toString(),
                receiverName = binding.edtRecipient.text.toString(),
                receiverPhoneNumber = binding.edtRecipientCellPhoneSecond.text.toString(),
                addressName = binding.edtDeliveryDestinationName.text.toString(),
                postalCode = "1111",
                address1 = binding.tvDestinationAddress.text.toString(),
                address2 = binding.edtDetailAddress.text.toString(),
                request = getString(R.string.please_put_it_in_front_of_the_door_if_im_absent),
                selectAsDefault = binding.cbSaveAsDefaultAddress.isChecked,
                paymentMethod = paymentMeansAdapter.selectedPaymentMethod
            )
            showTextLoadingDialog(this, "결제중입니다.")
            OrderAndPayActivityService(this).tryPostOrderAndPay(postRequest)


        }


        binding.ivBack.setOnClickListener {
            finish()
        }

    }

    override fun onBackPressed() {
        orderCancelDialog = OrderCancelDialog(this, this)
        orderCancelDialog.show()
    }

    fun setOrderProductList(): MutableList<OrderProductItemData>{

        var list = mutableListOf<OrderProductItemData>()
        list.add(orderProduct)
        return list
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

    override fun onPostOrderAndPaySuccess(response: OrderAndPayResponse) {
        dismissTextLoadingDialog()
        var intent = Intent(this, OrderResultActivity::class.java)
        intent.putExtra("optionName", orderProduct.optionName)
        intent.putExtra("address",
        binding.tvDestinationAddress.text.toString() + " " + binding.edtDetailAddress.text.toString())
        intent.putExtra("price", getDecimalFormat(orderProduct.price)+"원")
        startActivity(intent)
        finish()
    }

    override fun onPostOrderAndPayFailure(message: String) {
        dismissTextLoadingDialog()
        showCustomToast("상품 주문 요청 실패")
        Log.d("orderAndPayActivity", message)
    }

    fun getDecimalFormat(number: Int): String {
        val deciaml = DecimalFormat("#,###")
        return deciaml.format(number)
    }
}

