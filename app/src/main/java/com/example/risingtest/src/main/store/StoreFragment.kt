package com.example.risingtest.src.main.store

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment

import com.example.risingtest.databinding.FragmentStoreBinding
import com.example.risingtest.src.main.store.storeCategoryRv.StoreCategoryRvAdapter
import com.example.risingtest.src.main.store.storeCategoryRv.StoreCategoryRvItemData
import com.example.risingtest.src.main.store.storeMainRv.StoreMainRvAdapter
import com.example.risingtest.src.main.store.storeMainRv.StoreMainRvItemData
import com.example.risingtest.src.main.store.storeMainRv.storeSecondRv.ProductItemData
import com.example.risingtest.src.main.store.storeTopRv.StoreTopRvAdapter
import com.example.risingtest.src.main.store.storeTopRv.StoreTopRvItemData
import com.example.risingtest.src.scrap.ScrapActivity
import com.example.risingtest.src.shoppingBasket.ShoppingBasketActivity

class StoreFragment : BaseFragment<FragmentStoreBinding>(FragmentStoreBinding::bind, R.layout.fragment_store) {

    lateinit var storeTopAdapter: StoreTopRvAdapter
    lateinit var storeCategoryRvAdapter: StoreCategoryRvAdapter
    lateinit var storeMainAdapter: StoreMainRvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        storeMainAdapter = StoreMainRvAdapter(requireContext())
        storeMainAdapter.getListFromView(setMainRvItemList())
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
        list.add(StoreTopRvItemData(R.drawable.ic_store_autumn_blanket, R.string.store_rv_authmn_blanket, true))
        list.add(StoreTopRvItemData(R.drawable.ic_store_best, R.string.store_rv_best, false))
        list.add(StoreTopRvItemData(R.drawable.ic_store_todays_deal, R.string.store_rv_todays_deal, true))
        list.add(StoreTopRvItemData(R.drawable.ic_store_oh_goods, R.string.store_rv_oh_goods, true))
        list.add(StoreTopRvItemData(R.drawable.ic_store_quick_delivery, R.string.store_rv_quick_delivery, false))
        list.add(StoreTopRvItemData(R.drawable.ic_store_premiun, R.string.store_rv_premium, false))
        list.add(StoreTopRvItemData(R.drawable.ic_store_refurb_market, R.string.store_rv_refurb_market,false))
        list.add(StoreTopRvItemData(R.drawable.ic_store_new_special_price, R.string.store_rv_new_special_price, false))
        list.add(StoreTopRvItemData(R.drawable.ic_store_food_market, R.string.store_rv_food_market,true))
        list.add(StoreTopRvItemData(R.drawable.ic_store_exhibitions, R.string.store_rv_exhibitions, false))
        return list
    }

    fun setMainRvItemList(): MutableList<StoreMainRvItemData>{
        var mainList = mutableListOf<StoreMainRvItemData>()
        var prodList = mutableListOf<ProductItemData>()
        for(i in 0 until 5){
            prodList.add(ProductItemData())
        }
        mainList.add(StoreMainRvItemData(R.string.store_rv_main_title_todays_deal, true, "LH", prodList))

        prodList = mutableListOf<ProductItemData>()
        for(i in 0 until 5){
            prodList.add(ProductItemData())
        }
        mainList.add(StoreMainRvItemData(R.string.store_rv_main_title_product_for_user, true, "LH", prodList))

        prodList = mutableListOf<ProductItemData>()
        for(i in 0 until 5){
            prodList.add(ProductItemData())
        }
        mainList.add(StoreMainRvItemData(R.string.store_rv_main_title_recent_product, false, "LH", prodList))

        prodList = mutableListOf<ProductItemData>()
        for(i in 0 until 5){
            prodList.add(ProductItemData())
        }
        mainList.add(StoreMainRvItemData(R.string.store_rv_main_title_related_product, true, "LH", prodList))


        prodList = mutableListOf<ProductItemData>()
        for(i in 0 until 5){
            prodList.add(ProductItemData())
        }
        mainList.add(StoreMainRvItemData(R.string.store_rv_main_title_popular_product,  false, "G2", prodList))
        return mainList
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


}