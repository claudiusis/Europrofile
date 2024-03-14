package com.example.europrofile.ui.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.europrofile.R
import com.example.europrofile.core.ui.BaseFragment
import com.example.europrofile.data.AuthResult
import com.example.europrofile.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?) -> FragmentLoginBinding = {
        inflater, container ->
        FragmentLoginBinding.inflate(inflater, container, false)
    }

    private val viewModel : LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputList = listOf(binding.authLogin, binding.authPassword)

        viewModel.authState.observe(viewLifecycleOwner) {
            when(it) {
                AuthResult.Loading -> binding.loading.visibility = View.VISIBLE
                is AuthResult.Error -> binding.loading.visibility = View.GONE
                is AuthResult.Success -> findNavController().navigate(R.id.action_loginFragment_to_mainPage)
            }
        }

        binding.loginBtn.setOnClickListener{
            val valid = inputList.map { it.isValid() }

            if (valid.all { it }){
                viewModel.sendCredentials(inputList.first().text(),
                    inputList.last().text())
            }
        }

        binding.linkToRegistr.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    companion object {

        fun newInstance() = LoginFragment()
    }
}