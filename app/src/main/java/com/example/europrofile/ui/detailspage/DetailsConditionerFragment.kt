package com.example.europrofile.ui.detailspage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.example.europrofile.R
import com.example.europrofile.databinding.FragmentDetailsConditionerBinding
import com.example.europrofile.ui.detailspage.inforecycler.DetailsInfoCondAdapter
import com.example.europrofile.ui.tabs.account.recycler.MyDecorator

class DetailsConditionerFragment : Fragment() {

    private lateinit var binding : FragmentDetailsConditionerBinding

    private val pageViewModel : DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsConditionerBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailsRv.layoutManager = LinearLayoutManager(this.requireContext())

        pageViewModel.title.observe(viewLifecycleOwner){
            binding.detailsTitle.text = it
        }

        pageViewModel.imageUrls.observe(viewLifecycleOwner){

            val imageList = ArrayList<SlideModel>()

            for (img in it){
                imageList.add(
                    SlideModel(img)
                )
            }
            binding.detailsSlider.setImageList(imageList)

        }

        pageViewModel.info.observe(viewLifecycleOwner){

            binding.detailsRv.adapter = DetailsInfoCondAdapter(it)
            val itemDecorator = MyDecorator()
            itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divide_line)!!)
            binding.detailsRv.addItemDecoration(itemDecorator)

        }

        pageViewModel.description.observe(viewLifecycleOwner){
            binding.detailsDescription.text = it
        }

    }

}