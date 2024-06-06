package com.example.europrofile.ui.accountpages.mycomments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.europrofile.R
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentMyCommentsBinding
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import com.example.europrofile.ui.accountpages.reviewcreation.ReviewViewModel
import com.example.europrofile.ui.tabs.comments.recycler.ReviewAdapter
import com.example.europrofile.ui.tabs.comments.recycler.ViewReview
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCommentsFragment : Fragment() {

    private lateinit var binding : FragmentMyCommentsBinding

    private val chatsViewModel : ReviewViewModel by activityViewModels()
    private val user : ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyCommentsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myComRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        val adapter = ReviewAdapter(chatsViewModel.getUserReviews((user.userInfo.value as RequestResult.Success).data.id) as ArrayList<ViewReview>, { type, reviewId ->

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
        binding.myComRecycler.adapter = adapter

        chatsViewModel.reviewGetState.observe(viewLifecycleOwner){

            val myList = chatsViewModel.getUserReviews((user.userInfo.value as RequestResult.Success).data.id)

            adapter.changeList(myList)

            if (myList.isEmpty()){
                binding.emptyImg.visibility = View.VISIBLE
                binding.emptyText.visibility = View.VISIBLE
            } else {
                binding.emptyImg.visibility = View.GONE
                binding.emptyText.visibility = View.GONE
            }

        }
    }

}