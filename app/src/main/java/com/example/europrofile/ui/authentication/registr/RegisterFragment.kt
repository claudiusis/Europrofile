package com.example.europrofile.ui.authentication.registr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.europrofile.R
import com.example.europrofile.core.ui.BaseFragment
import com.example.europrofile.data.AuthResult
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
                AuthResult.Loading -> binding.loading.visibility = View.VISIBLE
                is AuthResult.Success -> findNavController().navigate(R.id.action_registerFragment_to_mainPage)
                is AuthResult.Error -> binding.loading.visibility = View.GONE
            }
        }

        binding.loginBtn.setOnClickListener {
            val valid = inputList.map { it.isValid() }

            if (valid.all { it }) {
                viewModel.sendCredentials(
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