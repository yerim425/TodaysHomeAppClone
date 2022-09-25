package com.example.risingtest.src.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentHomeBinding
import com.example.risingtest.src.main.home.homeMainRv.HomeMainRvAdapter
import com.example.risingtest.src.main.home.homeMainRv.HomeMainRvItemData
import com.example.risingtest.src.main.home.homeMainRv.homeSecondRv.HomePopularPostItemData
import com.example.risingtest.src.main.store.storeTopRv.HomeTopRvAdapter
import com.example.risingtest.src.main.store.storeTopRv.HomeTopRvItemData
import com.example.risingtest.src.scrap.ScrapActivity
import com.example.risingtest.src.shoppingBasket.ShoppingBasketActivity


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {


    lateinit var homeTopAdapter: HomeTopRvAdapter
    lateinit var homeMainRvAdapter: HomeMainRvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ivHomeAppbarScrap.setOnClickListener {
            startActivity(Intent(requireContext(), ScrapActivity::class.java))
        }

        binding.ivHomeAppbarBasket.setOnClickListener {
            startActivity(Intent(requireContext(), ShoppingBasketActivity::class.java))
        }


        // 맨 위의 리사이클러뷰 어댑터
        homeTopAdapter = HomeTopRvAdapter(requireActivity())
        homeTopAdapter.getListFromView(setTopRvItemList())
        binding.rvHomeTop.layoutManager = GridLayoutManager(requireContext(), 5)
        binding.rvHomeTop.adapter = homeTopAdapter

        // 메인 리사이클러뷰 어댑터
        homeMainRvAdapter = HomeMainRvAdapter(requireContext())
        homeMainRvAdapter.getListFromView(setMainRvItemList())
        binding.rvHomeMain.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHomeMain.adapter = homeMainRvAdapter

    }

    fun setTopRvItemList(): MutableList<HomeTopRvItemData>{
        var list = mutableListOf<HomeTopRvItemData>()
        list.add(HomeTopRvItemData(R.drawable.ic_home_shopping, R.string.shopping))
        list.add(HomeTopRvItemData(R.drawable.ic_home_quick_delivery, R.string.quick_delivery))
        list.add(HomeTopRvItemData(R.drawable.ic_home_house_warming, R.string.n_pyeong_house_warming))
        list.add(HomeTopRvItemData(R.drawable.ic_home_pictures_by_space, R.string.pictures_by_space))
        list.add(HomeTopRvItemData(R.drawable.ic_home_remodeling, R.string.remodeling))
        list.add(HomeTopRvItemData(R.drawable.ic_home_easy_move, R.string.easy_move))
        list.add(HomeTopRvItemData(R.drawable.ic_home_todays_deal, R.string.todays_deal))
        list.add(HomeTopRvItemData(R.drawable.ic_home_food_market, R.string.food_market))
        list.add(HomeTopRvItemData(R.drawable.ic_home_everyday_0won_deal, R.string.everyday_0won_deal))
        list.add(HomeTopRvItemData(R.drawable.ic_home_seazon_special_price, R.string.seazon_special_price))
        return list
    }

    fun setMainRvItemList(): MutableList<HomeMainRvItemData>{
        var mainList = mutableListOf<HomeMainRvItemData>()

        var postList = mutableListOf<HomePopularPostItemData>()
        postList.add(HomePopularPostItemData(R.drawable.img_home_popular_post1, false,
            "라이프스타일과", "취향을 담은 리모델링"))
        postList.add(HomePopularPostItemData(R.drawable.img_home_popular_post2, false,
            "싱그러운 원룸", "시선 닿는 곳마다 식물 가득"))
        postList.add(HomePopularPostItemData(R.drawable.img_home_popular_post3, false,
            "독립 없이도", "꿈꾸던 자취집처럼 변한 방"))
        postList.add(HomePopularPostItemData(R.drawable.img_home_popular_post4, false,
            "초록 붙박이 심폐소생", "숲처럼 가꾼 오피스텔"))
        mainList.add(HomeMainRvItemData("내 방은 나만의 작은 우주", false, "G2", postList))

        var postList2 = mutableListOf<HomePopularPostItemData>()
        postList2.add(HomePopularPostItemData(R.drawable.img_home_popular_post1, false,
            "탐나는 포인트가 가득", "매력 넘치는 숲속 집"))
        postList2.add(HomePopularPostItemData(R.drawable.img_home_popular_post2, false,
            "30년 된 구축", "시선 가는 곳마다 예쁜 신혼집으로"))
        postList2.add(HomePopularPostItemData(R.drawable.img_home_popular_post3, false,
            "어두운 기본 신축", "딥네이비&옐로우로 밸런스 맞추기"))
        postList2.add(HomePopularPostItemData(R.drawable.img_home_popular_post4, false,
            "히든 라인의 깔끔함!", "미니멀한 신혼부부의 집"))
        mainList.add(HomeMainRvItemData("각양각색 신혼집 인테리어", false, "G2", postList2))

        return mainList
    }


}