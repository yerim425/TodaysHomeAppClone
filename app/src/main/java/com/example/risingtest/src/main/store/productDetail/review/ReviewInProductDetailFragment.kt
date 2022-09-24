package com.example.risingtest.src.main.store.productDetail.review

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentReviewInProductDetailBinding


class ReviewInProductDetailFragment : BaseFragment<FragmentReviewInProductDetailBinding>(
    FragmentReviewInProductDetailBinding::bind, R.layout.fragment_review_in_product_detail) {

    lateinit var reviewRatingAdapter: ReviewRatingRvAdapter
    lateinit var reviewPhotosAdapter: ReviewPhotosRvAdapter
    lateinit var usersReviewRvAdapter: UsersReviewRvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 리뷰 평점
        reviewRatingAdapter = ReviewRatingRvAdapter(requireContext())
        reviewRatingAdapter.getListFromView(setReviewRatingListData())
        binding.rvRatingBar.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvRatingBar.adapter = reviewRatingAdapter

        // 상품 리뷰 이미지
        reviewPhotosAdapter = ReviewPhotosRvAdapter()
        reviewPhotosAdapter.getListFromView(setReviewPhotosListData())
        binding.rvReviewPhotos.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvReviewPhotos.adapter = reviewPhotosAdapter


        // 유저 리뷰들
        usersReviewRvAdapter = UsersReviewRvAdapter()
        usersReviewRvAdapter.getListFromView(setUsersReviewListData())
        binding.rvUsersReview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvUsersReview.adapter = usersReviewRvAdapter

    }

    fun setReviewRatingListData(): MutableList<ReviewRatingItemData>{
        var list = mutableListOf<ReviewRatingItemData>()
        list.add(ReviewRatingItemData("5점", 50, 100, true))
        list.add(ReviewRatingItemData("4점", 25, 100, false))
        list.add(ReviewRatingItemData("3점", 10, 100, false))
        list.add(ReviewRatingItemData("2점", 6, 100,false))
        list.add(ReviewRatingItemData("1점", 9, 100, false))
        return list
    }

    fun setReviewPhotosListData(): MutableList<Int>{
        var list = mutableListOf<Int>()
        list.add(R.drawable.img_prod1_1)
        list.add(R.drawable.img_prod1_2)
        list.add(R.drawable.img_prod1_3)
        list.add(R.drawable.img_prod2)
        list.add(R.drawable.img_prod3)
        return list
    }

    fun setUsersReviewListData(): MutableList<UsersReviewItemData>{
        var list = mutableListOf<UsersReviewItemData>()
        list.add(UsersReviewItemData())
        list.add(UsersReviewItemData())
        list.add(UsersReviewItemData())
        return list
    }
}