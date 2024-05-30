package com.example.europrofile.ui.accountpages.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.europrofile.R
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    private val viewModel : ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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


                    binding.iconText.setTextColor(resources.getColor(R.color.white))
                    binding.iconText.text = if (it.data.name.split(" ").size==2)
                        it.data.name[0].toString() + (it.data.name.split(" ")[1][0]).toString()
                    else it.data.name[0].toString()


                }
                is RequestResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.profileName.text = (viewModel.userInfo.value as RequestResult.Error).e.message.toString()
                }
            }

        }
    }

}