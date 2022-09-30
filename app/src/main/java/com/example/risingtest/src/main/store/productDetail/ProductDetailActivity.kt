package com.example.risingtest.src.main.store.productDetail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingtest.config.BaseActivity
import com.example.risingtest.databinding.ActivityProductDetailBinding
import com.example.risingtest.src.main.models.productScrap.ProductScrapResponse
import com.example.risingtest.src.main.models.productScrapCancel.ProductScrapCancelResponse
import com.example.risingtest.src.main.store.StoreFragmentService
import com.example.risingtest.src.main.store.productDetail.adapters.ProductDetailCategoryRvAdapter
import com.example.risingtest.src.main.store.productDetail.adapters.ProductImageViewPagerAdapter
import com.example.risingtest.src.main.store.productDetail.adapters.ProductExplainRvAdapter
import com.example.risingtest.src.main.store.productDetail.adapters.UsersStylingShotRvAdapter
import com.example.risingtest.src.main.store.productDetail.adapters.productDetailRvMainAdapter.ProductDetailRvMainAdapter
import com.example.risingtest.src.main.store.productDetail.adapters.productDetailRvMainAdapter.ProductDetailRvMainItemData
import com.example.risingtest.src.main.store.productDetail.models.productDetail.ProductDetailResponse
import com.example.risingtest.src.main.store.productDetail.purchase.dialogs.ProductPurchaseDialog
import com.example.risingtest.src.main.store.productDetail.review.NoReviewInProductDetialFragment
import com.example.risingtest.src.main.store.productDetail.review.ReviewInProductDetailFragment
import com.example.risingtest.src.main.store.storeFragment
import com.example.risingtest.src.scrap.ScrapActivity
import com.google.android.material.tabs.TabLayout
import java.text.DecimalFormat
import java.util.*
import kotlin.properties.Delegates

lateinit var productPurchaseDialog: ProductPurchaseDialog

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(ActivityProductDetailBinding::inflate)
    , ProductDetailActivityInterface, TabLayout.OnTabSelectedListener{

    lateinit var productDetailCategoryAdapter: ProductDetailCategoryRvAdapter
    lateinit var usersStylingShotAdapter: UsersStylingShotRvAdapter
    lateinit var productImageViewPagerAdapter: ProductImageViewPagerAdapter
    lateinit var productExplainRvAdapter: ProductExplainRvAdapter
    lateinit var productDetailRvMainAdapter: ProductDetailRvMainAdapter

    var firstStart = true
    lateinit var todayDate: String // 오늘 날짜
    var scrapNum = -1
    var userIdx = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userIdx = intent.getIntExtra("userIdx", -1)

        // 엑티비티 에니메이션
        if(intent.hasExtra("newActivity")){
            var newActivityAnim = intent.getStringExtra("newActivity")?.toInt()
            var preActivityAnim = intent.getStringExtra("preActivity")?.toInt()
            overridePendingTransition(newActivityAnim!!, preActivityAnim!!)
        }

        ProductDetailActivityService(this).tryGetProductDetail(intent.getIntExtra("productId", -1))
        binding.layoutLoadingWhite.foreground = ColorDrawable(Color.WHITE)
        binding.layoutPurchase.foreground = ColorDrawable(Color.WHITE)
        showLoadingDialog(this)

        // 상단의 상품 카테고리
        productDetailCategoryAdapter = ProductDetailCategoryRvAdapter()
        binding.rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategory.adapter = productDetailCategoryAdapter

        // 상단의 상품 이미지 뷰페이저
        productImageViewPagerAdapter = ProductImageViewPagerAdapter()
        binding.viewPagerProductImage.adapter = productImageViewPagerAdapter

        binding.viewPagerProductImage.offscreenPageLimit = 1
        binding.viewPagerProductImage.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPagerProductImage.setCurrentItem(0, false)


        //
        // 유저들의 스타일링샷
        usersStylingShotAdapter = UsersStylingShotRvAdapter()
        binding.rvUserStylingShot.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        binding.rvUserStylingShot.adapter = usersStylingShotAdapter

        // 탭 레이아웃
        var tab1 = layoutInflater.inflate(R.layout.tab_product_detail, null)
        tab1.findViewById<TextView>(R.id.title).text = getString(R.string.product_information)
        tab1.findViewById<TextView>(R.id.subTitle).visibility = View.INVISIBLE
        binding.layoutTab.getTabAt(0)?.setCustomView(tab1)

        var tab2 = layoutInflater.inflate(R.layout.tab_product_detail, null)
        tab2.findViewById<TextView>(R.id.title).text = getString(R.string.review)
        //tab2.findViewById<TextView>(R.id.subTitle).text = response.result.reviews.size
        tab2.findViewById<TextView>(R.id.subTitle).visibility = View.VISIBLE
        binding.layoutTab.getTabAt(1)?.setCustomView(tab2)

        var tab3 = layoutInflater.inflate(R.layout.tab_product_detail, null)
        tab3.findViewById<TextView>(R.id.title).text = getString(R.string.inquiry)
        tab3.findViewById<TextView>(R.id.subTitle).visibility = View.VISIBLE
        binding.layoutTab.getTabAt(2)?.setCustomView(tab3)

        var tab4 = layoutInflater.inflate(R.layout.tab_product_detail, null)
        tab4.findViewById<TextView>(R.id.title).text = getString(R.string.delivery_refund)
        tab4.findViewById<TextView>(R.id.subTitle).visibility = View.INVISIBLE
        binding.layoutTab.getTabAt(3)?.setCustomView(tab4)

        var tab5 = layoutInflater.inflate(R.layout.tab_product_detail, null)
        tab5.findViewById<TextView>(R.id.title).text = getString(R.string.recommendation)
        tab5.findViewById<TextView>(R.id.subTitle).visibility = View.INVISIBLE
        binding.layoutTab.getTabAt(4)?.setCustomView(tab5)

        //binding.layoutTab.addOnTabSelectedListener(this)

        //
        // 상품 설명 이미지
        productExplainRvAdapter = ProductExplainRvAdapter()
        binding.rvProductExplain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvProductExplain.adapter = productExplainRvAdapter

        // 문의, 배송/교환/환불, 추천
        productDetailRvMainAdapter = ProductDetailRvMainAdapter()
        productDetailRvMainAdapter.getListFromView(setMainListData())
        binding.rvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMain.adapter = productDetailRvMainAdapter


        scrapNum = Random().nextInt(1500)+1


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
        list.add(ProductDetailRvMainItemData(getString(R.string.inquiry), true, 0))
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

    override fun onGetProductDetailSuccess(response: ProductDetailResponse) {
        dismissLoadingDialog()


        // 상단 카테고리
        val getCategoryList = response.result.categoryList
        var categoryNameList = mutableListOf<String>()
        for(category in getCategoryList){
            categoryNameList.add(category.categoryName)
        }
        productDetailCategoryAdapter.getListFromView(categoryNameList)

        // 상단의 상품 이미지 뷰페이저
        val getProductPhotos = response.result.productPhotos
        productImageViewPagerAdapter.getListOfView(getProductPhotos)
        binding.viewPagerTotalNum.text = getProductPhotos.size.toString()
        binding.viewPagerProductImage.apply{
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.viewPagerCurrentNum.text = ((position%(getProductPhotos.size))+1).toString()
                }
            })
        }

        if(intent.getStringExtra("remainDate") != null){
            binding.layoutRemainDateTodaysDeal.visibility = View.VISIBLE
            binding.tvRemainDateTodaysDeal.text = intent.getStringExtra("remainDate") + "일 남음"
        }else{
            binding.layoutRemainDateTodaysDeal.visibility = View.GONE
        }

        // 상품 정보
        binding.tvTitleAppbar.text = response.result.productName

        binding.tvTitle.text = response.result.productName

        binding.tvProductBrandTop.text = response.result.brandName

        binding.ratingBar.rating = response.result.totalScore

        binding.tvReviewNumTop.text = "("+response.result.numReviews+")"

        var discountedPrice = response.result.discountedPrice // 할인할 가격
        var originalPrice = response.result.price
        if(response.result.discountedPrice == 0){ // 할인 제품이 아니라면
            binding.layoutDiscount.visibility = View.GONE
            binding.tvProductDiscountedPrice.text = getDecimalFormat(originalPrice)

            binding.tvSpecialPrice.visibility = View.GONE
            binding.tvImmediateDiscount.visibility = View.GONE
        }else{ // 할인 제품이라면
            binding.layoutDiscount.visibility = View.VISIBLE
            var discountedPercentage = getdiscountPercentage(originalPrice, discountedPrice)
            binding.tvDiscountPercentage.text = discountedPercentage.toString() + "%"
            binding.tvOriginalPrice.text = getDecimalFormat(originalPrice)
            binding.tvProductDiscountedPrice.text = getDecimalFormat(originalPrice-discountedPrice)

            binding.tvSpecialPrice.visibility = View.VISIBLE
            binding.tvImmediateDiscount.visibility = View.VISIBLE
        }

        var getPoints = ((originalPrice.toDouble()-discountedPrice.toDouble())*0.001).toInt()
        binding.tvAccumulatePoint.text = getPoints.toString() + "P"

        binding.tvArriveDate.text = getProductArriveDate()

        binding.tvProductBrandBottom.text = response.result.brandName

        binding.tvUsersStylingShotNum.text = getDecimalFormat(response.result.reviews.size)

        var tab2 = binding.layoutTab.getTabAt(1)
        tab2!!.view.findViewById<TextView>(R.id.subTitle).text = response.result.reviews.size.toString()

        // 상품 설명 사진
        productExplainRvAdapter.getListOfView(response.result.expPhotos)


        if(response.result.reviews.size > 0){  // review가 있다면
            binding.tvReviewNum.text = response.result.reviews.size.toString()
            binding.tvReviewNum.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().replace(
                R.id.frm_review, ReviewInProductDetailFragment(response.result.reviews)).commitAllowingStateLoss()
        }else{ // 없다면
            binding.tvReviewNum.visibility = View.GONE
            supportFragmentManager.beginTransaction().replace(R.id.frm_review, NoReviewInProductDetialFragment()).commitAllowingStateLoss()
        }

        // 유저 스타일링 샷 -> 리뷰 사진으로 대체
        var usersStylingPhotoList = mutableListOf<String>()
        for(review in response.result.reviews){
            usersStylingPhotoList.add(review.reviewPhotoUrl)
        }
        usersStylingShotAdapter.getListFromView(usersStylingPhotoList)


        // 스크랩 여부
        var scrapProduct = "scrapProduct"+response.result.productId
        var isScrapped = sSharedPreferences.getString(scrapProduct, "false")
        binding.cbScrap.isChecked = isScrapped == "true"
        binding.cbScrap.setOnClickListener {
            var editor = sSharedPreferences.edit()

            if(binding.cbScrap.isChecked){
                ProductDetailActivityService(this).tryPostProductScrap(
                    userIdx, response.result.productId)

                editor.putString(scrapProduct, "true")
                scrapNum++
            }else{
                ProductDetailActivityService(this).tryPatchProductScrapCancel(
                    userIdx, response.result.productId)

                editor.putString(scrapProduct, "false")
                scrapNum--
            }
            editor.commit()
            storeFragment.storeMainAdapter.notifyDataSetChanged()
            binding.tvScrapNum.text = getDecimalFormat(scrapNum)
        }
        binding.tvScrapNum.text = getDecimalFormat(scrapNum)

        // 구매하기 버튼 리스너
        binding.btnPurchase.setOnClickListener {
            productPurchaseDialog = ProductPurchaseDialog(this, response.result)
            productPurchaseDialog.show()
        }

        binding.layoutLoadingWhite.foreground = ColorDrawable(Color.TRANSPARENT)
        binding.layoutPurchase.foreground = ColorDrawable(Color.TRANSPARENT)
    }

    override fun onGetProductDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("상품 상세 화면 요청 실패")
    }


    override fun onPostProductScrapSuccess(response: ProductScrapResponse) {
        createToast(this)?.show()
    }

    override fun onPostProductScrapFailure(message: String) {
        showCustomToast("상품 스크랩 요청 실패")
        Log.d("productDetailActivity", message)
    }

    override fun onPatchProductScrapSuccess(response: ProductScrapCancelResponse) {
    }

    override fun onPatchProductScrapFailure(message: String) {
        showCustomToast("상품 스크랩 취소 요청 실패")
        Log.d("productDetailActivity", message)

    }




    override fun onTabSelected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }





    fun getDecimalFormat(number: Int): String {
        val deciaml = DecimalFormat("#,###")
        return deciaml.format(number)
    }

    fun getProductArriveDate(): String{
        var calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 5) // 5일 뒤 날짜
        val year = calendar.get(Calendar.YEAR).toString()
        val month = (calendar.get(Calendar.MONTH) + 1).toString()
        val day = calendar.get(Calendar.DATE).toString()
        var nWeek = calendar.get(Calendar.DAY_OF_WEEK)
        var strWeek = "" // 요일
        when(nWeek){
            1 -> strWeek="일"
            2 -> strWeek="월"
            3 -> strWeek="화"
            4 -> strWeek="수"
            5 -> strWeek="목"
            6 -> strWeek="금"
            7 -> strWeek="토"
        }
        return ("${month}/${day}(${strWeek})")
    }

    fun getdiscountPercentage(originalPrice: Int, discountedPrice: Int): Int{
        return (discountedPrice.toDouble()/originalPrice.toDouble()*100.0).toInt()

    }

    fun createToast(context: Context): Toast?{
        val inflater = LayoutInflater.from(context)
        val binding: ViewDataBinding? =
            DataBindingUtil.inflate(inflater, R.layout.toast_scrap, null, false)

        binding?.root?.findViewById<TextView>(R.id.btn_see_scrapbook)?.setOnClickListener {
            startActivity(Intent(this, ScrapActivity::class.java))
        }


        return Toast(context).apply {
            setGravity(Gravity.BOTTOM, 0, 0)
            duration = Toast.LENGTH_SHORT
            view = binding?.root
        }
    }

}