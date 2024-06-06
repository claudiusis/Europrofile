package com.example.europrofile.ui.accountpages.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.europrofile.R
import com.example.europrofile.databinding.FragmentAboutUsBinding
import com.example.europrofile.ui.accountpages.info.recycler.Info
import com.example.europrofile.ui.accountpages.info.recycler.InfoRecyclerAdapter

class AboutUsFragment : Fragment() {

    private lateinit var binding : FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutUsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.infoRecycler.adapter = InfoRecyclerAdapter(addInfo())
        binding.infoRecycler.layoutManager = LinearLayoutManager(requireContext())

    }

    //
    private fun addInfo() : List<Info>  {
        val list = mutableListOf<Info>().apply {

            add(
                Info(R.drawable.crown, resources.getString(R.string.first_info))
            )

            add(
                Info(R.drawable.group, resources.getString(R.string.second_info))
            )
            add(
                Info(R.drawable.discuss, resources.getString(R.string.third_info))
            )

            add(
                Info(R.drawable.gear, resources.getString(R.string.fourth_info))
            )

        }
        return list
    }

}