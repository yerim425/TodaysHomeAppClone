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
import com.example.risingtest.databinding.ToastShoppingCartBinding
import com.example.risingtest.src.main.store.productDetail.purchase.adapters.ProductOptionAdapter
import com.example.risingtest.src.main.store.productDetail.purchase.adapters.ProductOptionSelectedAdapter
import com.example.risingtest.src.main.store.productDetail.purchase.datas.ProductOptionDetailItemData
import com.example.risingtest.src.main.store.productDetail.purchase.datas.ProductOptionItemData
import com.example.risingtest.src.orderAndPay.OrderAndPayActivity
import com.example.risingtest.src.shoppingBasket.ShoppingBasketActivity

class ProductPurchaseDialog(context: Context, var text: String?= null): Dialog(context) {
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



        productOptionAdapter = ProductOptionAdapter(context)
        productOptionAdapter.getListFromView(setOptionList())
        binding.rvProductOption.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvProductOption.adapter = productOptionAdapter

        productOptionSelectedAdapter = ProductOptionSelectedAdapter()
        binding.rvProductSelected.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvProductSelected.adapter = productOptionSelectedAdapter


        binding.btnShoppingBasket.setOnClickListener {
            if(productOptionSelectedAdapter.itemCount == 0){
                Toast.makeText(context, R.string.please_selected_product_option, Toast.LENGTH_SHORT).show()
            }else{
                dismiss()

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
                context.startActivity(intent)

            }
        }

    }

    override fun show() {
        if(!this.isShowing) super.show()
    }


    fun setOptionList(): MutableList<ProductOptionItemData>{
        var mainList = mutableListOf<ProductOptionItemData>()

        var secondList1 = arrayListOf<ProductOptionDetailItemData>()
        secondList1.add(ProductOptionDetailItemData("화이트", "10,000원"))
        secondList1.add(ProductOptionDetailItemData("블랙", "10,000원"))
        secondList1.add(ProductOptionDetailItemData("블루", "10,000원"))
        secondList1.add(ProductOptionDetailItemData("레드", "10,000원"))
        secondList1.add(ProductOptionDetailItemData("그레이", "10,000원"))
        secondList1.add(ProductOptionDetailItemData("옐로우", "10,000원"))
        secondList1.add(ProductOptionDetailItemData("그린", "10,000원"))
        mainList.add(ProductOptionItemData("선택", secondList1))

        return mainList
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