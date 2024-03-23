package com.example.europrofile.ui.tabs.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.europrofile.R
import com.example.europrofile.databinding.FragmentMainPageBinding
import com.example.europrofile.ui.tabs.main.newsrecycler.ExamplesAdapter
import com.example.europrofile.ui.tabs.main.newsrecycler.Image
import com.example.europrofile.ui.tabs.main.newsrecycler.NewsAdapter

class MainPage : Fragment() {

    private lateinit var binding: FragmentMainPageBinding

    private lateinit var viewPager2: ViewPager2
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsList: ArrayList<Image>
    private lateinit var exampleList: ArrayList<Image>
    private lateinit var exampleAdapter: ExamplesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager2 = binding.newsPager
        newsAdapter = NewsAdapter(makeNews(), viewPager2)

        viewPager2.adapter = newsAdapter

        viewPager2 = binding.workExamples
        exampleAdapter = ExamplesAdapter(makeExamples())

        viewPager2.adapter = exampleAdapter

    }

    private fun makeNews(): MutableList<Image> {
        newsList = ArrayList<Image>()
        newsList.add(Image(R.drawable.img_2))
        newsList.add(Image(R.drawable.img_3))
        newsList.add(Image(R.drawable.img_4))
        newsList.add(Image(R.drawable.img_5))
        return newsList
    }

    private fun makeExamples(): List<Image> {
        exampleList = ArrayList<Image>()
        exampleList.add(Image(R.drawable.ex_1))
        exampleList.add(Image(R.drawable.ex_2))
        exampleList.add(Image(R.drawable.ex_3))
        exampleList.add(Image(R.drawable.ex_4))
        exampleList.add(Image(R.drawable.ex_5))
        return exampleList
    }

}
