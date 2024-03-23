package com.example.europrofile.ui.reviewcreation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.databinding.FragmentReviewCreationFragmentBinding
import com.example.europrofile.ui.tabs.comments.recycler.ReviewImgAdapter

class ReviewCreationFragment : Fragment() {

    private lateinit var binding: FragmentReviewCreationFragmentBinding
    private lateinit var recyclerView: RecyclerView

    val REQUEST_CODE = 1000

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
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
            if (result.resultCode==Activity.RESULT_OK && result.resultCode==REQUEST_CODE){
                val data = result?.data

            }
        }

        binding.addImgCreationBtn.setOnClickListener {
            val imgIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(imgIntent)
        }

    }

}