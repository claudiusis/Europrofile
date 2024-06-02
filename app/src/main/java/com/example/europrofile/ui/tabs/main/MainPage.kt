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
import com.example.europrofile.domain.User
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import com.example.europrofile.ui.detailspage.DetailsViewModel
import com.example.europrofile.ui.tabs.main.condition.CondTypeCard
import com.example.europrofile.ui.tabs.main.condition.ConditionParentAdapter
import com.example.europrofile.ui.tabs.main.condition.ConditionerViewModel
import com.example.europrofile.ui.tabs.main.newsrecycler.Image
import com.example.europrofile.ui.tabs.main.newsrecycler.NewsAdapter
import com.example.europrofile.ui.tabs.main.windowrecycler.ExamplesAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainPage : Fragment() {

    private lateinit var binding: FragmentMainPageBinding

    private val viewModelUser : ProfileViewModel by activityViewModels()

    private lateinit var viewPager2: ViewPager2
    private lateinit var newsAdapter: NewsAdapter

    private lateinit var newsList: ArrayList<Image>
    private lateinit var exampleAdapter: ExamplesAdapter

    private lateinit var conditionParentAdapter: ConditionParentAdapter

    @Inject
    lateinit var viewModelAssistedFactory : ConditionerViewModel.Factory


    private val viewModelCond : ConditionerViewModel by activityViewModels() {
        ConditionerViewModel.provideFactory(viewModelAssistedFactory, User())
    }
    private val detailsViewModel : DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(layoutInflater)

        val uid = requireContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("UID", "-1")?:"-1"

        viewModelUser.getUserInfo(uid)

/*        viewModelUser.userInfo.observe(viewLifecycleOwner){
            if (it is RequestResult.Success) {
                viewModelCond.getConditionType(it.data)
            }
        }*/

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager2 = binding.newsPager
        newsAdapter = NewsAdapter(makeNews(), viewPager2)

        viewPager2.adapter = newsAdapter

        viewPager2 = binding.workExamples
        exampleAdapter = ExamplesAdapter()

        viewPager2.adapter = exampleAdapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.text){
                    "Кондиционеры" -> {
                        binding.condLayout.visibility = View.VISIBLE
                        binding.windLayout.visibility = View.GONE
                        binding.condRecycler.setPadding(0,0,0,120)
                    }
                    "Все товары" -> {
                        binding.condLayout.visibility = View.VISIBLE
                        binding.windLayout.visibility = View.VISIBLE
                        binding.condRecycler.setPadding(0,0,0,0)
                    }
                    "Окна" -> {
                        binding.condLayout.visibility = View.GONE
                        binding.windLayout.visibility = View.VISIBLE
                        binding.condRecycler.setPadding(0,0,0,0)
                    }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
        })


        val condAdapter = ConditionParentAdapter(arrayListOf(), { link, imgList ->
            detailsViewModel.getData(link, imgList)
            findNavController().navigate(R.id.action_mainPage_to_detailsConditionerFragment)
        }, { elem ->
            viewModelCond.addFavourites(elem)
        })
        binding.condRecycler.adapter = condAdapter
        binding.condRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModelCond.condCard.observe(viewLifecycleOwner){
            condAdapter.addItems(it as ArrayList<CondTypeCard>)
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
