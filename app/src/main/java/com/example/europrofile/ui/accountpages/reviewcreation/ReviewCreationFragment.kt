package com.example.europrofile.ui.accountpages.reviewcreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.databinding.FragmentReviewCreationFragmentBinding
import com.example.europrofile.ui.tabs.comments.recycler.ReviewImg
import com.example.europrofile.ui.tabs.comments.recycler.ReviewImgAdapter

class ReviewCreationFragment : Fragment() {

    private lateinit var binding: FragmentReviewCreationFragmentBinding
    private lateinit var recyclerView: RecyclerView

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
        val adapter = ReviewImgAdapter(ArrayList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            val img = ReviewImg(it!!)

            adapter.addImg(img)
        }

        binding.addImgCreationBtn.setOnClickListener {
            resultLauncher.launch("image/*")
        }

    }

}