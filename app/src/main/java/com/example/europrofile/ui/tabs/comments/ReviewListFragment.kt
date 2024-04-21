package com.example.europrofile.ui.tabs.comments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentReviewListBinding
import com.example.europrofile.ui.accountpages.reviewcreation.ReviewViewModel
import com.example.europrofile.ui.tabs.comments.recycler.ReviewAdapter

class ReviewListFragment : Fragment() {

    private lateinit var binding: FragmentReviewListBinding
    private lateinit var recyclerView: RecyclerView

    private val chatsViewModel : ReviewViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.reviewRv
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        chatsViewModel.getReviews()

        chatsViewModel.reviewGetState.observe(viewLifecycleOwner){

            when (it) {
                is RequestResult.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is RequestResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    recyclerView.adapter = ReviewAdapter(it.data)
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    Log.i("QWERTY", (it as RequestResult.Error).e.message.toString())
                }
            }

        }

    }
}