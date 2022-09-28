package com.example.risingtest.src.main.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentHomeBinding
import com.example.risingtest.src.main.home.homeMainRv.HomeMainRvAdapter
import com.example.risingtest.src.main.home.homeMainRv.HomeMainRvItemData
import com.example.risingtest.src.main.home.homeMainRv.homeSecondRv.HomePopularPostItemData
import com.example.risingtest.src.main.store.StoreViewPagerAdapter
import com.example.risingtest.src.main.store.storeTopRv.HomeTopRvAdapter
import com.example.risingtest.src.main.store.storeTopRv.HomeTopRvItemData
import com.example.risingtest.src.scrap.ScrapActivity
import com.example.risingtest.src.shoppingBasket.ShoppingBasketActivity


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {


    lateinit var homeTopAdapter: HomeTopRvAdapter
    lateinit var homeMainRvAdapter: HomeMainRvAdapter

    lateinit var pagerAdapter: HomeViewPagerAdapter
    private var myHandler = ViewPagerHandler()
    private val intervalTime = 5000.toLong() // 5초
    private var currentPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰페이저
        pagerAdapter = HomeViewPagerAdapter()
        pagerAdapter.getListOfView(setViewPagerImageList())
        binding.viewPagerHome.adapter = pagerAdapter

        binding.viewPagerHome.offscreenPageLimit = 1
        binding.viewPagerHome.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPagerHome.setCurrentItem(currentPosition, false)

        binding.viewPagerHome.apply{
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

    fun setViewPagerImageList(): MutableList<Int>{

        var list = mutableListOf<Int>()
        list.add(R.drawable.img_home_viewpager_1)
        list.add(R.drawable.img_home_viewpager_2)
        list.add(R.drawable.img_home_viewpager_3)
        list.add(R.drawable.img_home_viewpager_4)
        list.add(R.drawable.img_home_viewpager_5)
        list.add(R.drawable.img_home_viewpager_6)
        list.add(R.drawable.img_home_viewpager_7)
        list.add(R.drawable.img_home_viewpager_8)
        list.add(R.drawable.img_home_viewpager_9)
        list.add(R.drawable.img_home_viewpager_10)
        list.add(R.drawable.img_home_viewpager_11)
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
                binding.viewPagerHome.setCurrentItem(++currentPosition, true)
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

}