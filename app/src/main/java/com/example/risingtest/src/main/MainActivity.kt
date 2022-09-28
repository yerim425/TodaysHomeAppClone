package com.example.risingtest.src.main

import android.animation.ObjectAnimator
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.risingtest.R
import com.example.risingtest.config.ApplicationClass.Companion.sSharedPreferences
import com.example.risingtest.config.BaseActivity
import com.example.risingtest.databinding.ActivityMainBinding
import com.example.risingtest.src.main.home.HomeFragment
import com.example.risingtest.src.main.models.MyProfileResponse
import com.example.risingtest.src.main.moveConstructRepair.MoveConstructRepairFragment
import com.example.risingtest.src.main.myPage.MyPageFragment
import com.example.risingtest.src.main.store.StoreFragment
import com.example.risingtest.src.main.upload.UploadTypeAdapter
import com.example.risingtest.src.main.upload.UploadTypeItemData

lateinit var homeFragment: HomeFragment
lateinit var shoppingFragment: StoreFragment
lateinit var moveConstructRepairFragment: MoveConstructRepairFragment
lateinit var myPageFragment: MyPageFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), MainActivityInterface {

    lateinit var uploadTypeAdapter: UploadTypeAdapter
    var firstStart = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var userIdx = sSharedPreferences.getString("userIdx", null)?.toInt()
        Log.d("MainActivity", userIdx.toString())
        MainService(this).tryGetMyProfile(userIdx!!)

        supportFragmentManager.beginTransaction().replace(R.id.frm_main, HomeFragment()).commitAllowingStateLoss()
        binding.btmNavMain.setOnItemSelectedListener {
            when(it.itemId){
                R.id.main_btm_nav_home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frm_main, homeFragment).commitAllowingStateLoss()
                    true
                }
                R.id.main_btm_nav_shopping -> {
                    shoppingFragment = StoreFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frm_main, shoppingFragment).commitAllowingStateLoss()
                    true
                }
                R.id.main_btm_nav_construct -> {
                    moveConstructRepairFragment = MoveConstructRepairFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frm_main, moveConstructRepairFragment).commitAllowingStateLoss()
                    true
                }
                R.id.main_btm_nav_my_page -> {
                    myPageFragment = MyPageFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frm_main, myPageFragment).commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }

        // 메인 업로드 화면 리사이클러뷰
        uploadTypeAdapter = UploadTypeAdapter()
        uploadTypeAdapter.getListFromView(setUploadTypeItemList())
        binding.rvMainUploadType.adapter = uploadTypeAdapter


        val offset = resources.getDimension(R.dimen.main_upload_view_height)

        binding.tvUploadNotion.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)

        // 플로팅 버튼(업로드 화면 띄움) 클릭 리스너
        binding.floatingBtnUpload.setOnClickListener {
            if(binding.layoutUpload.visibility == View.GONE){
                ObjectAnimator.ofFloat(binding.floatingBtnUpload, View.ROTATION, 0f, 45f).start()
                binding.layoutUpload.visibility = View.VISIBLE
                binding.frmEllipse.visibility = View.VISIBLE

                ObjectAnimator.ofFloat(binding.layoutUpload, View.TRANSLATION_Y, offset, 0f).start()

            }else{
                ObjectAnimator.ofFloat(binding.floatingBtnUpload, View.ROTATION, 45f, 0f).start()
                binding.layoutUpload.translationY = offset
                binding.frmEllipse.visibility = View.GONE
                binding.layoutUpload.visibility = View.GONE
            }
        }
    }

    private fun setUploadTypeItemList(): MutableList<UploadTypeItemData>{
        var list = mutableListOf<UploadTypeItemData>()

        list.add(UploadTypeItemData(R.drawable.ic_upload_photo,
            getString(R.string.main_upload_photo), getString(R.string.main_upload_photo_benefit)))

        list.add(UploadTypeItemData(R.drawable.ic_upload_vedio,
            getString(R.string.main_upload_vedio), getString(R.string.main_upload_vedio_benefit)))

        list.add(UploadTypeItemData(R.drawable.ic_upload_review_product,
            getString(R.string.main_upload_review_product), getString(R.string.main_upload_review_product_benefit)))

        list.add(UploadTypeItemData(R.drawable.ic_upload_review_interior_construction,
            getString(R.string.main_upload_review_interior_construction), getString(R.string.main_upload_review_interior_construction_benefit)))

        list.add(UploadTypeItemData(R.drawable.ic_upload_question_interior,
            getString(R.string.main_upload_question_interior)))

        return list
    }

    override fun onResume() {
        super.onResume()
        if(!firstStart)
            overridePendingTransition(R.drawable.anim_slide_in_left, R.drawable.anim_slide_out_right)

        firstStart = false
    }

    override fun onGetMyProfileSuccess(response: MyProfileResponse) {
        var editor = sSharedPreferences.edit()
        //editor.putString("userNickName", response.result.nickname)
        //editor.commit()
    }

    override fun onGetMyProfileFailure(message: String) {
        showCustomToast("MainActivity: getMyProfile 실패")
    }
}