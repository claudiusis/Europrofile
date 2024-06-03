package com.example.europrofile.ui.tabs.comments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentReviewListBinding
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import com.example.europrofile.ui.accountpages.reviewcreation.ReviewViewModel
import com.example.europrofile.ui.tabs.comments.recycler.ReviewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewListFragment : Fragment() {

    private lateinit var binding: FragmentReviewListBinding
    private lateinit var recyclerView: RecyclerView

    private val chatsViewModel : ReviewViewModel by activityViewModels()
    private val user : ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatsViewModel.getReviews()

        recyclerView = binding.reviewRv
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        val adapter = ReviewAdapter(arrayListOf(), { type, reviewId ->

            chatsViewModel.updateReview(reviewId, type, (user.userInfo.value as RequestResult.Success).data.id)

        }, { review, data ->

            if (data.listOfUserLikes.contains((user.userInfo.value as RequestResult.Success).data.id)){
                review.likeImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.colorlike)
                review.dislikeImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.like)
                review.likeAmount.setTextColor(resources.getColor(R.color.blue))
                review.likeImg.setColorFilter(Color.parseColor("#0D6EFD"))
                review.dislikeImg.setColorFilter(R.color.black)
                review.dislikeAmount.setTextColor(resources.getColor(R.color.black))
            } else if (data.listOfUserDisLikes.contains((user.userInfo.value as RequestResult.Success).data.id)) {
                review.dislikeImg.setColorFilter(Color.parseColor("#0D6EFD"))
                review.dislikeAmount.setTextColor(resources.getColor(R.color.blue))
                review.dislikeImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.colorlike)
                review.likeImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.like)
                review.likeImg.setColorFilter(R.color.black)
                review.likeAmount.setTextColor(resources.getColor(R.color.black))
            } else {
                review.likeImg.setColorFilter(R.color.black)
                review.likeAmount.setTextColor(resources.getColor(R.color.black))
                review.dislikeImg.setColorFilter(R.color.black)
                review.dislikeAmount.setTextColor(resources.getColor(R.color.black))
                review.likeImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.like)
                review.dislikeImg.background = ContextCompat.getDrawable(requireContext(), R.drawable.like)
            }

        })
        recyclerView.adapter = adapter

        chatsViewModel.reviewGetState.observe(viewLifecycleOwner){

            adapter.changeList(it)

        }

    }
}