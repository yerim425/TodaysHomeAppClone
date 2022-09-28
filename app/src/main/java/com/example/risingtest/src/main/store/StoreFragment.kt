package com.example.risingtest.src.main.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingtest.config.BaseFragment

import com.example.risingtest.databinding.FragmentStoreBinding
import com.example.risingtest.src.main.store.productDetail.models.ProductDetailResponse
import com.example.risingtest.src.main.store.models.productScrap.ProductScrapResponse
import com.example.risingtest.src.main.store.models.storePage.ResultStorePage
import com.example.risingtest.src.main.store.models.storePage.StorePageResponse
import com.example.risingtest.src.main.store.models.storePage.StorePageProduct
import com.example.risingtest.src.main.store.storeCategoryRv.StoreCategoryRvAdapter
import com.example.risingtest.src.main.store.storeCategoryRv.StoreCategoryRvItemData
import com.example.risingtest.src.main.store.storeMainRv.StoreMainRvAdapter
import com.example.risingtest.src.main.store.storeMainRv.StoreMainRvItemData
import com.example.risingtest.src.main.store.storeTopRv.StoreTopRvAdapter
import com.example.risingtest.src.main.store.storeTopRv.StoreTopRvItemData
import com.example.risingtest.src.scrap.ScrapActivity
import com.example.risingtest.src.shoppingBasket.ShoppingBasketActivity

class StoreFragment : BaseFragment<FragmentStoreBinding>(FragmentStoreBinding::bind, R.layout.fragment_store),
    StoreFragmentInterface{

    lateinit var storeTopAdapter: StoreTopRvAdapter
    lateinit var storeCategoryRvAdapter: StoreCategoryRvAdapter
    lateinit var storeMainAdapter: StoreMainRvAdapter

    lateinit var pagerAdapter: StoreViewPagerAdapter
    private var myHandler = ViewPagerHandler()
    private val intervalTime = 5000.toLong() // 5초
    private var currentPosition = 0

    var userIdx: Int = -1
    lateinit var storePageResult : ResultStorePage

    lateinit var fragment : StoreFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment = this
        userIdx = sSharedPreferences.getString("userIdx", null)!!.toInt()
        Log.d("storeFragment", "userIdx="+userIdx.toString())
        showLoadingDialog(requireContext())
        StoreFragmentService(this).tryGetStorePage(userIdx)

        // 뷰페이저
        pagerAdapter = StoreViewPagerAdapter()
        pagerAdapter.getListOfView(setViewPagerImageList())
        binding.viewPagerStore.adapter = pagerAdapter

        binding.viewPagerStore.offscreenPageLimit = 1
        binding.viewPagerStore.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPagerStore.setCurrentItem(currentPosition, false)

        binding.viewPagerStore.apply{
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //binding.viewPagerCurrentNum.text = ((position%3)+1).toString()
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        // 뷰페이저에서 손 떼었을때 / 뷰페이저 멈춰있을 때
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                        // 뷰페이저 움직이는 중
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                }
            })
        }



        // 맨 위의 리사이클러뷰 어댑터
        storeTopAdapter = StoreTopRvAdapter()
        storeTopAdapter.getListFromView(setTopRvItemList())
        binding.rvStoreTop.layoutManager = GridLayoutManager(requireContext(), 5)
        binding.rvStoreTop.adapter = storeTopAdapter

        // 카테고리 리사이클러뷰 어댑터
        storeCategoryRvAdapter = StoreCategoryRvAdapter(requireContext())
        storeCategoryRvAdapter.getListFromView(setCategoryRvItemList())
        binding.rvStoreCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvStoreCategory.adapter = storeCategoryRvAdapter



        // 메인 리사이클러뷰 어댑터
        storeMainAdapter = StoreMainRvAdapter(requireContext(), this, userIdx)
        binding.rvStoreMain.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvStoreMain.adapter = storeMainAdapter




        binding.ivStoreAppbarScrap.setOnClickListener {
            startActivity(Intent(requireContext(), ScrapActivity::class.java))
        }

        binding.ivStoreAppbarBasket.setOnClickListener {
            startActivity(Intent(requireContext(), ShoppingBasketActivity::class.java))
        }

    }

    fun setTopRvItemList(): MutableList<StoreTopRvItemData>{
        var list = mutableListOf<StoreTopRvItemData>()
        list.add(StoreTopRvItemData(R.drawable.ic_store_autumn_blanket, R.string.authmn_blanket))
        list.add(StoreTopRvItemData(R.drawable.ic_store_best, R.string.best))
        list.add(StoreTopRvItemData(R.drawable.ic_store_todays_deal, R.string.todays_deal))
        list.add(StoreTopRvItemData(R.drawable.ic_store_oh_goods, R.string.oh_goods))
        list.add(StoreTopRvItemData(R.drawable.ic_store_quick_delivery, R.string.quick_delivery))
        list.add(StoreTopRvItemData(R.drawable.ic_store_premiun, R.string.premium))
        list.add(StoreTopRvItemData(R.drawable.ic_store_refurb_market, R.string.refurb_market))
        list.add(StoreTopRvItemData(R.drawable.ic_store_new_special_price, R.string.new_special_price))
        list.add(StoreTopRvItemData(R.drawable.ic_store_food_market, R.string.food_market))
        list.add(StoreTopRvItemData(R.drawable.ic_store_exhibitions, R.string.exhibitions))
        return list
    }

    fun setCategoryRvItemList(): MutableList<StoreCategoryRvItemData>{
        val list = mutableListOf<StoreCategoryRvItemData>()
        list.add(StoreCategoryRvItemData(R.drawable.ic_menu_blue,  R.string.category))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_furniture, R.string.store_rv_category_furniture))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_fabric, R.string.store_rv_category_fabric))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_home_appliance, R.string.store_rv_category_home_appliance))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_child, R.string.store_rv_category_child))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_light, R.string.store_rv_category_light))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_kitchenware, R.string.store_rv_category_kitchenware))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_deco_plant, R.string.store_rv_category_deco_plant))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_storage, R.string.store_rv_category_storage))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_daily_goods, R.string.store_rv_category_daily_goods))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_daily_necessity, R.string.store_rv_category_daily_necessity))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_tool, R.string.store_rv_category_tool))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_interior_construction, R.string.store_rv_category_interior_construction))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_companion_animal, R.string.store_rv_category_companion_animal))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_camping, R.string.store_rv_category_camping))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_indoor_exercise, R.string.store_rv_category_indoor_exercise))
        list.add(StoreCategoryRvItemData(R.drawable.ic_store_category_rental, R.string.store_rv_category_rental))

        return list
    }

    private fun autoScrollStart(intervalTime: Long) {
        myHandler.removeMessages(0)
        myHandler.sendEmptyMessageDelayed(0, intervalTime)
    }

    private fun autoScrollStop(){
        myHandler.removeMessages(0)
    }

    private inner class ViewPagerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if(msg.what == 0) {
                binding.viewPagerStore.setCurrentItem(++currentPosition, true)
                autoScrollStart(intervalTime)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
    }

    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }

    fun setViewPagerImageList(): MutableList<Int>{

        var list = mutableListOf<Int>()
        list.add(R.drawable.img_store_viewpager_1)
        list.add(R.drawable.img_store_viewpager_2)
        list.add(R.drawable.img_store_viewpager_3)
        list.add(R.drawable.img_store_viewpager_4)
        list.add(R.drawable.img_store_viewpager_5)
        list.add(R.drawable.img_store_viewpager_6)
        list.add(R.drawable.img_store_viewpager_7)
        list.add(R.drawable.img_store_viewpager_8)
        list.add(R.drawable.img_store_viewpager_9)
        list.add(R.drawable.img_store_viewpager_10)
        return list
    }

    override fun onGetStorePageSuccess(response: StorePageResponse) {
        dismissLoadingDialog()
        storePageResult = response.result

        var mainList = mutableListOf<StoreMainRvItemData>()
        // 오늘의 딜
        var todaysDealList = mutableListOf<StorePageProduct>()
        for(i in 0 until response.result.todaysDeal.size){
            todaysDealList.add(response.result.todaysDeal[i])
            todaysDealList[i].isTodaysDeal = true
        }
        mainList.add(StoreMainRvItemData(getString(R.string.store_rv_main_title_todays_deal), true, "LH", todaysDealList))


        // 유저를 위한 상품
        var userNickname = sSharedPreferences.getString("userNickName", null)
        var forUserList = mutableListOf<StorePageProduct>()
        for(i in 0 until response.result.favorites.size){
            forUserList.add(response.result.favorites[i])
        }
        var forUserTitle = userNickname + getString(R.string.store_rv_main_title_product_for_user)
        mainList.add(StoreMainRvItemData(forUserTitle, true, "LH", forUserList))


        // 인기 상품
        var popularList = mutableListOf<StorePageProduct>()
        for(i in 0 until response.result.favorites.size){
            popularList.add(response.result.favorites[i])
        }
        mainList.add(StoreMainRvItemData(getString(R.string.store_rv_main_title_popular_product), false, "G2", popularList))

        storeMainAdapter.getListFromView(mainList)


    }

    override fun onGetStorePageFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }


    override fun onPostProductScrapSuccess(response: ProductScrapResponse) {
        createToast(requireContext())?.show()

    }

    override fun onPostProductScrapFailure(message: String) {
        showCustomToast("상품 스크랩 실패")
    }

    override fun onGetProductDetailSuccess(response: ProductDetailResponse) {

    }

    override fun onGetProductDetailFailure(message: String) {
        showCustomToast("상품 상세 화면 요청 실패")
    }

    fun createToast(context: Context): Toast?{
        val inflater = LayoutInflater.from(context)
        val binding: ViewDataBinding? =
            DataBindingUtil.inflate(inflater, R.layout.toast_scrap, null, false)

        binding?.root?.findViewById<TextView>(R.id.btn_see_scrapbook)?.setOnClickListener {
            startActivity(Intent(requireContext(), ScrapActivity::class.java))
        }


        return Toast(context).apply {
            setGravity(Gravity.BOTTOM, 0, 0)
            duration = Toast.LENGTH_SHORT
            view = binding?.root
        }
    }
}

