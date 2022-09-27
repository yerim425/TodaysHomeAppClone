package com.example.risingtest.src.main.store.productDetail

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.risingtest.R
import com.example.risingtest.config.BaseActivity
import com.example.risingtest.databinding.ActivityProductDetailBinding
import com.example.risingtest.src.main.store.productDetail.adapters.ProductDetailCategoryRvAdapter
import com.example.risingtest.src.main.store.productDetail.adapters.ProductImageViewPagerAdapter
import com.example.risingtest.src.main.store.productDetail.adapters.ProductInformationRvAdapter
import com.example.risingtest.src.main.store.productDetail.adapters.UsersStylingShotRvAdapter
import com.example.risingtest.src.main.store.productDetail.adapters.productDetailRvMainAdapter.ProductDetailRvMainAdapter
import com.example.risingtest.src.main.store.productDetail.adapters.productDetailRvMainAdapter.ProductDetailRvMainItemData
import com.example.risingtest.src.main.store.productDetail.purchase.dialogs.ProductPurchaseDialog
import com.example.risingtest.src.main.store.productDetail.review.ReviewInProductDetailFragment

lateinit var productPurchaseDialog: ProductPurchaseDialog

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(ActivityProductDetailBinding::inflate) {

    lateinit var productDetailCategoryAdapter: ProductDetailCategoryRvAdapter
    lateinit var usersStylingShotAdapter: UsersStylingShotRvAdapter
    lateinit var productImageViewPagerAdapter: ProductImageViewPagerAdapter
    lateinit var productInformationRvAdapter: ProductInformationRvAdapter
    lateinit var productDetailRvMainAdapter: ProductDetailRvMainAdapter

    var firstStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 엑티비티 에니메이션
        if(intent.hasExtra("newActivity")){
            var newActivityAnim = intent.getStringExtra("newActivity")?.toInt()
            var preActivityAnim = intent.getStringExtra("preActivity")?.toInt()
            overridePendingTransition(newActivityAnim!!, preActivityAnim!!)
        }
        //overridePendingTransition(R.drawable.anim_slide_in_right, R.drawable.anim_slide_out_left)

        // 상단의 상품 카테고리
        productDetailCategoryAdapter = ProductDetailCategoryRvAdapter()
        productDetailCategoryAdapter.getListFromView(setCategoryListData())
        binding.rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategory.adapter = productDetailCategoryAdapter


        // 상단의 상품 이미지 뷰페이저
        productImageViewPagerAdapter = ProductImageViewPagerAdapter()
        productImageViewPagerAdapter.getListOfView(setViewPagerImageList())
        binding.viewPagerProductImage.adapter = productImageViewPagerAdapter

        binding.viewPagerProductImage.offscreenPageLimit = 1
        binding.viewPagerProductImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPagerProductImage.setCurrentItem(0, false)

        // 유저들의 스타일링샷
        usersStylingShotAdapter = UsersStylingShotRvAdapter()
        usersStylingShotAdapter.getListFromView(setUsersStylingShotListData())
        binding.rvUserStylingShot.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        binding.rvUserStylingShot.adapter = usersStylingShotAdapter

        // 상품 정보 이미지
        productInformationRvAdapter = ProductInformationRvAdapter()
        productInformationRvAdapter.getListOfView(setProductInfoListData())
        binding.rvProductInformation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvProductInformation.adapter = productInformationRvAdapter

        // review가 있다면
        supportFragmentManager.beginTransaction().replace(R.id.frm_review, ReviewInProductDetailFragment()).commitAllowingStateLoss()
        // 없다면
        //supportFragmentManager.beginTransaction().replace(R.id.frm_review, NoReviewInProductDetialFragment()).commitAllowingStateLoss()


        // 문의, 배송/교환/환불, 추천
        productDetailRvMainAdapter = ProductDetailRvMainAdapter()
        productDetailRvMainAdapter.getListFromView(setMainListData())
        binding.rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMain.adapter = productDetailRvMainAdapter


        binding.btnPurchase.setOnClickListener {
            productPurchaseDialog = ProductPurchaseDialog(this)
            productPurchaseDialog.show()
        }
    }


    private fun setCategoryListData(): MutableList<String>{
        var list = mutableListOf<String>()
        list.add("가구")
        list.add("서랍.수납장")
        list.add("수납장")
        return list
    }

    private fun setUsersStylingShotListData(): MutableList<Int>{
        var list = mutableListOf<Int>()
        list.add(R.drawable.img_prod1_1)
        list.add(R.drawable.img_prod1_2)
        list.add(R.drawable.img_prod1_3)
        list.add(R.drawable.img_prod2)
        list.add(R.drawable.img_prod3)
        return list
    }

    fun setViewPagerImageList(): MutableList<Int>{

        var list = mutableListOf<Int>()
        list.add(R.drawable.img_prod1_1)
        list.add(R.drawable.img_prod1_2)
        list.add(R.drawable.img_prod1_3)
        return list
    }

    fun setProductInfoListData(): MutableList<Int>{
        var list= mutableListOf<Int>()
        list.add(R.drawable.img_prod_info_1)
        list.add(R.drawable.img_prod_info_2)
        list.add(R.drawable.img_prod_info_3)
        list.add(R.drawable.img_prod_info_4)
        return list
    }

    fun setMainListData(): MutableList<ProductDetailRvMainItemData>{
        var list = mutableListOf<ProductDetailRvMainItemData>()
        list.add(ProductDetailRvMainItemData(getString(R.string.inquiry), true, 99))
        list.add(ProductDetailRvMainItemData(getString(R.string.delivery_exchange_refund), true))
        return list
    }

    override fun onResume() {
        super.onResume()
        if(!firstStart){
            overridePendingTransition(R.drawable.anim_slide_in_left, R.drawable.anim_slide_out_right)
        }
        firstStart = false
    }
}