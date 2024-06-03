package com.example.europrofile.ui.tabs.account

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.europrofile.R
import com.example.europrofile.core.ui.findTopNavController
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentAccountBinding
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import com.example.europrofile.ui.tabs.account.recycler.ChildItem
import com.example.europrofile.ui.tabs.account.recycler.ParentAdapter
import com.example.europrofile.ui.tabs.account.recycler.ParentItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding

    private lateinit var recycler : RecyclerView

    private val recyclerList = ArrayList<ParentItem>()
    private val viewModel : ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("QWERTY", viewModel.userInfo.value.toString())

        viewModel.userInfo.observe(viewLifecycleOwner){
            when(it){
                is RequestResult.Success -> {
                    binding.iconContainer.visibility = View.VISIBLE
                    binding.textName.visibility = View.VISIBLE

                    binding.textName.text = it.data.name
                    if (it.data.imgUri!=null){
                        binding.iconText.setTextColor(Color.TRANSPARENT)
                        Glide.with(requireContext()).load(it.data.imgUri)
                            .error(R.color.blue)
                            .placeholder(R.color.blue)
                            .into(binding.avatarIcon)
                    } else {
                        binding.iconText.setTextColor(resources.getColor(R.color.white))
                        binding.iconText.text = if (it.data.name.split(" ").size==2)
                            it.data.name[0].toString() + (it.data.name.split(" ")[1][0]).toString()
                        else it.data.name[0].toString()
                    }
                }
                else -> {
                    binding.iconContainer.visibility = View.GONE
                    binding.textName.visibility = View.GONE
                }
            }
        }

        binding.accInfoLayout.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_profileFragment)
        }

        recycler = binding.settingsRecycler
        recycler.layoutManager = LinearLayoutManager(context)

        addItems()

        val adapter = ParentAdapter({
                title  ->
            when (title) {
                "Написать отзыв" -> findNavController().navigate(R.id.action_accountFragment_to_reviewCreationFragment)
                "Контакты" -> findNavController().navigate(R.id.action_accountFragment_to_contactInformationFragment)
                "Мои отзывы" -> findNavController().navigate(R.id.action_accountFragment_to_myCommentsFragment)
                "О компании" -> findNavController().navigate(R.id.action_accountFragment_to_aboutUsFragment)
                "Выйти из аккаунта" -> {
                    viewModel.logOut()
                    val editor = requireContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit()
                    editor.remove("UID").apply()
                    findTopNavController().navigate(R.id.action_tabsFragment_to_loginFragment, null, navOptions {
                        popUpTo(R.id.tabsFragment){
                            inclusive = true
                        }
                    })
                }
            }
        }, recyclerList)
        recycler.adapter = adapter
    }

    private fun addItems(){
        recyclerList.clear()
        val settingsList = ArrayList<ChildItem>()
        settingsList.add(ChildItem("Записи на замер", R.drawable.baseline_arrow_forward_ios_24))
        settingsList.add(ChildItem("Написать отзыв", R.drawable.baseline_arrow_forward_ios_24))
        settingsList.add(ChildItem("Мои отзывы", R.drawable.baseline_arrow_forward_ios_24))
        recyclerList.add(ParentItem("Возможности", settingsList))
        val settingsList2 = ArrayList<ChildItem>()
        settingsList2.add(ChildItem("О компании", R.drawable.baseline_arrow_forward_ios_24))
        settingsList2.add(ChildItem("Контакты", R.drawable.baseline_arrow_forward_ios_24))
        settingsList2.add(ChildItem("Выйти из аккаунта", R.drawable.baseline_arrow_forward_ios_24))
        recyclerList.add(ParentItem("Дополнительно", settingsList2))
    }

}