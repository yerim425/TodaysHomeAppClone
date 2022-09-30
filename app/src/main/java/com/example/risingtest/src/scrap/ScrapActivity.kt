package com.example.risingtest.src.scrap

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass
import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingtest.config.BaseActivity
import com.example.risingtest.databinding.ActivityScrapBinding
import com.example.risingtest.src.scrap.fragments.scrapAll.ScrapedAllFragment
import com.example.risingtest.src.scrap.fragments.ScrapFolderFragment
import com.example.risingtest.src.scrap.fragments.scrapAll.ScrapedAllFragmentInterface
import com.example.risingtest.src.scrap.fragments.scrapAll.ScrapedAllService
import com.example.risingtest.src.scrap.fragments.scrapAll.scrapedAllItem.ResultScrapedAllItem
import com.example.risingtest.src.scrap.fragments.scrapProduct.ScrapedProductFragment
import com.example.risingtest.src.scrap.fragments.scrapAll.scrapedAllItem.ScrabItem
import com.example.risingtest.src.scrap.fragments.scrapAll.scrapedAllItem.ScrapedAllItemResponse
import com.google.android.material.tabs.TabLayoutMediator

lateinit var scrapedAllFragment: ScrapedAllFragment
lateinit var scrapedProductFragment: ScrapedProductFragment


class ScrapActivity : BaseActivity<ActivityScrapBinding>(ActivityScrapBinding::inflate),
    ScrapedAllFragmentInterface {

    var tabTitleArray = arrayOf("모두(0)", "폴더", "상품(0)")
    lateinit var scrapedAllResult : ResultScrapedAllItem
    lateinit var activity: ScrapActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = this
        // 엑티비티 에니메이션
        overridePendingTransition(R.drawable.anim_slide_in_right, R.drawable.anim_slide_out_left)

        binding.tvUserNickname.text = sSharedPreferences.getString("userNickName", "null")

        // 스크랩한 모든 아이템 조회
        showLoadingDialog(this)
        ScrapedAllService(this)
            .tryGetScrapedAllItem(ApplicationClass.sSharedPreferences.getString("userIdx", "-1")!!.toInt())





        binding.ivBack.setOnClickListener {
            finish()
        }

    }

    inner class ScrapViewPagerAdapter(supportFragmentManager: FragmentManager, lifecycle: Lifecycle)
        : FragmentStateAdapter(supportFragmentManager, lifecycle) {
        override fun getItemCount(): Int = tabTitleArray.size

        override fun createFragment(position: Int): Fragment {

            when(position){
                0 -> {
                    scrapedAllFragment = ScrapedAllFragment(activity)
                    return scrapedAllFragment
                }
                1 -> {
                    return ScrapFolderFragment(activity)
                }
                2 -> {
                    scrapedProductFragment = ScrapedProductFragment(activity)
                    return scrapedProductFragment
                }
                else -> {
                    scrapedAllFragment = ScrapedAllFragment(activity)
                    return scrapedAllFragment
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scrap_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_toolbar_share -> {
                true
            }
            else -> onOptionsItemSelected(item)
        }
    }

    override fun onGetScrapedAllItemSuccess(response: ScrapedAllItemResponse) {
        dismissLoadingDialog()

        scrapedAllResult = response.result
        val productCount = response.result.scrabBanner.scrabProductCount

        binding.viewPagerScrap.adapter = ScrapViewPagerAdapter(supportFragmentManager, lifecycle)

        if(productCount > 0){
            binding.viewPagerScrap.currentItem = 2
        }else{
            binding.viewPagerScrap.currentItem = 0
        }

        // 탭 타이틀 셋팅
        TabLayoutMediator(binding.layoutTab, binding.viewPagerScrap) { tab, position ->

            if(productCount > 0){
                tabTitleArray = arrayOf(
                    "모두(" + response.result.scrabBanner.scrabTotalCount + ")",
                    "폴더",
                    "상품(" + response.result.scrabBanner.scrabProductCount + ")"
                )
            }else{
                tabTitleArray = arrayOf(
                    "모두(" + response.result.scrabBanner.scrabTotalCount + ")",
                    "폴더",
                    ""
                )
            }

            tab.text = tabTitleArray[position]
        }.attach()
    }

    override fun onGetScrapedAllItemFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("스크랩 아이템 조회 요청 실패")
        Log.d("scrapActivity", message)
    }

}