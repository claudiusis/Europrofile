package com.example.europrofile.ui.accountpages.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.europrofile.databinding.FragmentAboutUsBinding
import com.example.europrofile.ui.accountpages.info.recycler.Info

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


    }

    //
    fun addInfo() : List<Info>  {
        val list = mutableListOf<Info>().apply {

        }

        return list
    }

}