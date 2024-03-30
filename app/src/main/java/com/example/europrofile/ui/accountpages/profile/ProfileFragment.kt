package com.example.europrofile.ui.accountpages.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    private val viewModel : ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserInfo()

        viewModel.userInfo.observe(viewLifecycleOwner){

            when (it) {
                is RequestResult.Loading ->  {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is RequestResult.Success -> {
                    val result = viewModel.userInfo.value as RequestResult.Success
                    binding.progressBar.visibility = View.GONE

                    binding.profileName.text = result.data.name
                    binding.profileInfo1.text = result.data.number
                    binding.profileInfo2.text = result.data.email
                    binding.profileInfo3.text = result.data.address
                    binding.profileInfo4.text = "⚫".repeat(result.data.password.length)

                }
                is RequestResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.profileName.text = (viewModel.userInfo.value as RequestResult.Error).e.message.toString()
                }
            }

        }
    }

}