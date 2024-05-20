package com.example.europrofile.ui.tabs.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.europrofile.R
import com.example.europrofile.databinding.FragmentMainPageBinding
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import com.example.europrofile.ui.detailspage.DetailsViewModel
import com.example.europrofile.ui.tabs.main.condition.ConditionParentAdapter
import com.example.europrofile.ui.tabs.main.condition.ConditionerViewModel
import com.example.europrofile.ui.tabs.main.newsrecycler.Image
import com.example.europrofile.ui.tabs.main.newsrecycler.NewsAdapter
import com.example.europrofile.ui.tabs.main.windowrecycler.ExamplesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPage : Fragment() {

    private lateinit var binding: FragmentMainPageBinding

    private lateinit var viewPager2: ViewPager2
    private lateinit var newsAdapter: NewsAdapter

    private lateinit var newsList: ArrayList<Image>
    private lateinit var exampleAdapter: ExamplesAdapter

    private lateinit var conditionParentAdapter: ConditionParentAdapter

    private val viewModelUser : ProfileViewModel by activityViewModels()
    private val viewModelCond : ConditionerViewModel by activityViewModels()
    private val detailsViewModel : DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(layoutInflater)

        viewModelCond.getConditionType()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uid = requireContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("UID", "-1")?:"-1"

        viewModelUser.getUserInfo(uid)

        viewPager2 = binding.newsPager
        newsAdapter = NewsAdapter(makeNews(), viewPager2)

        viewPager2.adapter = newsAdapter

        viewPager2 = binding.workExamples
        exampleAdapter = ExamplesAdapter()

        viewPager2.adapter = exampleAdapter


        val condAdapter = ConditionParentAdapter() { link, imgList ->
            detailsViewModel.getData(link, imgList)
            findNavController().navigate(R.id.action_mainPage_to_detailsConditionerFragment)
        }
        binding.condRecycler.adapter = condAdapter
        binding.condRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModelCond.condCard.observe(viewLifecycleOwner){
            condAdapter.addItems(it.last())
        }
    }


    private fun makeNews(): MutableList<Image> {
        newsList = ArrayList()
        newsList.add(Image("https://firebasestorage.googleapis.com/v0/b/europrofile-b9677.appspot.com/o/img_2.png?alt=media&token=e78f36f9-bb8a-4bb5-9d8c-83aef320a159"))
        newsList.add(Image("https://firebasestorage.googleapis.com/v0/b/europrofile-b9677.appspot.com/o/img_3.png?alt=media&token=012cc6bf-888d-45ad-8095-2f675ba08f07"))
        newsList.add(Image("https://firebasestorage.googleapis.com/v0/b/europrofile-b9677.appspot.com/o/img_4.png?alt=media&token=29b19c69-c84b-4ded-bf13-0113754cb65b"))
        newsList.add(Image("https://firebasestorage.googleapis.com/v0/b/europrofile-b9677.appspot.com/o/img_5.png?alt=media&token=7b0a45ff-dfd6-46ed-a0ef-56959a04144a"))
        return newsList
    }

}
