package com.example.europrofile.ui.authentication.registr

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.europrofile.R
import com.example.europrofile.core.ui.BaseFragment
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentRegistrBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegistrBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?) -> FragmentRegistrBinding = {
            inflater, context -> FragmentRegistrBinding.inflate(inflater, context, false)
    }

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputList = listOf(binding.regMail, binding.regPassword, binding.regTelephone)

        viewModel.authState.observe(viewLifecycleOwner){
            when(it){
                RequestResult.Loading -> {
                    binding.loading.visibility = View.VISIBLE

                }
                is RequestResult.Success -> {
                    val editor = requireContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit()
                    editor.putString("UID", it.data.id).apply()
                    Log.i("QWERTY", "All normal")
                    findNavController().navigate(R.id.action_registerFragment_to_tabsFragment)
                } // findNavController().navigate(R.id.action_registerFragment_to_mainPage)
                is RequestResult.Error -> {
                    binding.loading.visibility = View.GONE
                    Log.i("QWERTY", it.e.localizedMessage.toString())
                }
            }
        }

        binding.loginBtn.setOnClickListener {
            val valid = inputList.map { it.isValid() }

            if (valid.all { it }) {
                viewModel.sendCredentials(
                    binding.username.text.toString(),
                    binding.regTelephone.text(),
                    binding.regMail.text(),
                    binding.regPassword.text()
                )
            }
        }

        binding.linkToAuth.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}