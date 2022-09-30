package com.example.risingtest.src.main.store.productDetail.purchase.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.risingtest.R
import com.example.risingtest.databinding.DialogProductPurchaseBinding
import com.example.risingtest.src.main.store.productDetail.models.productDetail.ResultProductDetail
import com.example.risingtest.src.main.store.productDetail.purchase.adapters.ProductOptionAdapter
import com.example.risingtest.src.main.store.productDetail.purchase.adapters.ProductOptionSelectedAdapter
import com.example.risingtest.src.main.store.productDetail.purchase.datas.ProductOptionItemData
import com.example.risingtest.src.orderAndPay.OrderAndPayActivity
import com.example.risingtest.src.shoppingBasket.ShoppingBasketActivity
import java.text.DecimalFormat

var productTotalPrice = 0

class ProductPurchaseDialog(context: Context, val productDetail: ResultProductDetail): Dialog(context) {
    private lateinit var binding: DialogProductPurchaseBinding

    lateinit var productOptionAdapter: ProductOptionAdapter
    lateinit var productOptionSelectedAdapter: ProductOptionSelectedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogProductPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        window!!.setDimAmount(0.3f)
        // Custom Dialog 크기 조절
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // Custom Dialog 위치 조절
        window?.setGravity(Gravity.BOTTOM)
        // Custom Dialog 배경 설정
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        // 옵션 리사이클러뷰
        productOptionAdapter = ProductOptionAdapter(context)
        productOptionAdapter.getListFromView(setOptionList())
        binding.rvProductOption.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvProductOption.adapter = productOptionAdapter

        // 선택한 옵션 리사이클러뷰
        productOptionSelectedAdapter = ProductOptionSelectedAdapter(this, productDetail.price-productDetail.discountedPrice)
        binding.rvProductSelected.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvProductSelected.adapter = productOptionSelectedAdapter

        binding.orderPrice.text = "0원"

        binding.btnShoppingBasket.setOnClickListener {
            if(productOptionSelectedAdapter.itemCount == 0){
                Toast.makeText(context, R.string.please_selected_product_option, Toast.LENGTH_SHORT).show()
            }else{
                dismiss()

                productTotalPrice = 0
                // 다른 유저가 함께 구매한 상품이 없으면
                ShoppingCartToast.createToast(context)?.show()

                // 다른 유저가 함께 구매한 상품이 있으면

            }
        }

        binding.btnBuyRightNow.setOnClickListener {
            if(productOptionSelectedAdapter.itemCount == 0){
                Toast.makeText(context, R.string.please_selected_product_option, Toast.LENGTH_SHORT).show()
            }else{
                dismiss()

                val intent = Intent(context, OrderAndPayActivity::class.java)
                intent.putExtra("newActivity", R.drawable.anim_slide_in_right.toString())
                intent.putExtra("preActivity", R.drawable.anim_slide_out_left.toString())
                intent.putExtra("isProductDetail", "true")
                intent.putExtra("brandName", productDetail.brandName)
                intent.putExtra("imageUrl", productDetail.productPhotos[0])
                intent.putExtra("productName", productDetail.productName)
                intent.putExtra("optionName", productOptionSelectedAdapter.getOptionName())
                intent.putExtra("optionId", productOptionSelectedAdapter.getOptionId())
                intent.putExtra("price", productTotalPrice)
                intent.putExtra("productNum", productOptionSelectedAdapter.getProductNum())
                intent.putExtra("productId", productOptionSelectedAdapter.getProductId())

                context.startActivity(intent)

            }
        }

    }

    override fun show() {
        if(!this.isShowing) super.show()
    }

    override fun onBackPressed() {
        productTotalPrice = 0
        dismiss()
    }


    fun setOptionList(): MutableList<ProductOptionItemData>{
        var mainList = mutableListOf<ProductOptionItemData>()

        mainList.add(ProductOptionItemData(
            "선택", (productDetail.price-productDetail.discountedPrice), productDetail.options))

        return mainList
    }

    fun setProductTotalPrice(price: Int){
        binding.orderPrice.text = getDecimalFormat(price) + "원"
    }

    fun getDecimalFormat(number: Int): String {
        val deciaml = DecimalFormat("#,###")
        return deciaml.format(number)
    }
}

object ShoppingCartToast{

    fun createToast(context: Context): Toast?{
        val inflater = LayoutInflater.from(context)
        val binding: ViewDataBinding? =
            DataBindingUtil.inflate(inflater, R.layout.toast_shopping_cart, null, false)
        //val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //val view = inflater.inflate(R.layout.toast_shopping_cart, null)

        binding?.root?.findViewById<TextView>(R.id.tv_short_cut)?.setOnClickListener {
            context.startActivity(Intent(context, ShoppingBasketActivity::class.java))
        }

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 55.toPx())
            duration = Toast.LENGTH_LONG
            view = binding?.root
        }
    }
    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}