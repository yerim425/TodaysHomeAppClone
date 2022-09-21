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
import com.example.risingtest.config.BaseActivity
import com.example.risingtest.databinding.ActivityScrapBinding
import com.example.risingtest.src.scrap.fragments.ScrapAllFragment
import com.example.risingtest.src.scrap.fragments.ScrapFolderFragment
import com.example.risingtest.src.scrap.fragments.ScrapProductFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ScrapActivity : BaseActivity<ActivityScrapBinding>(ActivityScrapBinding::inflate) {

    private val tabTitleArray = arrayOf("모두(0)", "폴더", "상품(0)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 엑티비티 에니메이션
        overridePendingTransition(R.drawable.anim_slide_in_right, R.drawable.anim_slide_out_left)

        binding.viewPagerScrap.adapter = ScrapViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPagerScrap.currentItem = 0

        // 탭 타이틀 셋팅
        TabLayoutMediator(binding.layoutTab, binding.viewPagerScrap) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()



        binding.ivBack.setOnClickListener {
            finish()
        }

    }

    inner class ScrapViewPagerAdapter(supportFragmentManager: FragmentManager, lifecycle: Lifecycle)
        : FragmentStateAdapter(supportFragmentManager, lifecycle) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {

            return when (position) {
                0 -> ScrapAllFragment()
                1 -> ScrapFolderFragment()
                2 -> ScrapProductFragment()
                else -> ScrapProductFragment()
            }
        }

    }

    //item 버튼 메뉴 Toolbar에 집어 넣기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scrap_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId){
            android.R.id.home -> {
                showCustomToast("뒤로가기 버튼 클릭")
                finish()
                true
            }
            R.id.menu_toolbar_share -> {
                showCustomToast("공유 버튼 클릭")
                true
            }
            else -> onOptionsItemSelected(item)
        }
    }
}