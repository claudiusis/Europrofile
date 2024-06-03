package com.example.europrofile.ui.tabs.addorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.europrofile.databinding.FragmentMakeOrderBinding

class MakeOrderFragment : Fragment() {

    private lateinit var binding : FragmentMakeOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMakeOrderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrowImg.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}