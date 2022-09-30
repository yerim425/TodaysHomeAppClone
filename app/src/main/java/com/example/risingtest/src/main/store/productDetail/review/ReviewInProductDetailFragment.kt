package com.example.risingtest.src.main.store.productDetail.review

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.risingtest.R
import com.example.risingtest.config.BaseFragment
import com.example.risingtest.databinding.FragmentReviewInProductDetailBinding
import com.example.risingtest.src.main.store.productDetail.models.productDetail.Review
import java.text.DecimalFormat
import kotlin.math.roundToInt


class ReviewInProductDetailFragment(val reviews: List<Review>) : BaseFragment<FragmentReviewInProductDetailBinding>(
    FragmentReviewInProductDetailBinding::bind, R.layout.fragment_review_in_product_detail) {

    lateinit var reviewRatingAdapter: ReviewRatingRvAdapter
    lateinit var reviewPhotosAdapter: ReviewPhotosRvAdapter
    lateinit var usersReviewRvAdapter: UsersReviewRvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var scoreSum = 0
        for(review in reviews){
            scoreSum += review.score
        }
        var scoreAvg = scoreSum.toFloat()/reviews.size.toFloat()
        binding.tvRating.text = ((scoreAvg * 10.0).roundToInt() / 10.0).toString()
        binding.ratingBarStar.rating = scoreAvg


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

        // 리뷰 더보기
        binding.tvSeeMoreReviewNum.text = "( "+ getDecimalFormat(reviews.size) + " )"
    }

    fun setReviewRatingListData(): MutableList<ReviewRatingItemData>{
        var list = mutableListOf<ReviewRatingItemData>()
        var totalNum = reviews.size
        var reviewNumList = mutableListOf(0, 0, 0, 0, 0)
        for(review in reviews){
            when(review.score){
                1 -> reviewNumList[0]++
                2 -> reviewNumList[1]++
                3 -> reviewNumList[2]++
                4 -> reviewNumList[3]++
                5 -> reviewNumList[4]++
            }
        }
        var isMostList = mutableListOf(false, false, false, false, false)
        var maxScoreIdx = 0
        for(i in 1 until reviewNumList.size){
            if(reviewNumList[i] > reviewNumList[maxScoreIdx]){
                maxScoreIdx = i
            }
        }
        isMostList[maxScoreIdx] = true
        for(i in 0 until reviewNumList.size){
            if(reviewNumList[i] == reviewNumList[maxScoreIdx]){
                isMostList[i] = true
            }
        }
        list.add(ReviewRatingItemData("5점", reviewNumList[4], totalNum, isMostList[4]))
        list.add(ReviewRatingItemData("4점", reviewNumList[3], totalNum, isMostList[3]))
        list.add(ReviewRatingItemData("3점", reviewNumList[2], totalNum, isMostList[2]))
        list.add(ReviewRatingItemData("2점", reviewNumList[1], totalNum,isMostList[2]))
        list.add(ReviewRatingItemData("1점", reviewNumList[0], totalNum, isMostList[0]))
        return list
    }

    fun setReviewPhotosListData(): MutableList<String>{
        var list = mutableListOf<String>()
        for(review in reviews){
            list.add(review.reviewPhotoUrl)
        }
        return list
    }

    fun setUsersReviewListData(): MutableList<Review>{
        var list = mutableListOf<Review>()
        for(i in 0 until reviews.size){
            list.add(reviews[i])
            if(i == 2) break
        }
        return list
    }

    fun getDecimalFormat(number: Int): String {
        val deciaml = DecimalFormat("#,###")
        return deciaml.format(number)
    }

}