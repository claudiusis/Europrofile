package com.example.europrofile.ui.reviewcreation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentReviewCreationFragmentBinding
import com.example.europrofile.domain.Review
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class ReviewCreationFragment : Fragment() {

    private lateinit var binding: FragmentReviewCreationFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val reviewViewModel : ReviewViewModel by activityViewModels()
    private val userInfo : ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewCreationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.imageAdditionReviewCreatorRv
        val adapter = ReviewImgAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it!=null) {
                adapter.addImg(it)
            }
        }

        binding.addImgCreationBtn.setOnClickListener {
            resultLauncher.launch("image/*")
        }

        binding.finishReviewCreationBtn.setOnClickListener{

            val userData = (userInfo.userInfo.value as RequestResult.Success).data

            val stringUri = mutableListOf<String>()
            adapter.imgList.forEach { stringUri.add(it.toString()) }

            val review = Review(
                userData.id + "-" + userData.countOfReviews.toString(),
                (userInfo.userInfo.value as RequestResult.Success).data.id,
                Date(),
                stringUri,
                binding.reviewCreationInput.text.toString(),
                mutableListOf(),
                mutableListOf()
            )

            reviewViewModel.addReview(
                review
            )

        }

        reviewViewModel.reviewSetState.observe(viewLifecycleOwner){
            when(it){
                is  RequestResult.Success -> {
                    findNavController().popBackStack()
                }
                is RequestResult.Loading -> {
                    Log.i("QWERTY", "Loading")
                }
                else -> {
                    Log.i("QWERTY", (it as RequestResult.Error).e.toString())
                }
            }
        }



    }

}