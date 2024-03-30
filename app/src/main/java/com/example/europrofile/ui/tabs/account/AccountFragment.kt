package com.example.europrofile.ui.tabs.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.europrofile.R
import com.example.europrofile.databinding.FragmentAccountBinding
import com.example.europrofile.ui.tabs.account.recycler.ChildItem
import com.example.europrofile.ui.tabs.account.recycler.ParentAdapter
import com.example.europrofile.ui.tabs.account.recycler.ParentItem

class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding

    private lateinit var recycler : RecyclerView

    private val recyclerList = ArrayList<ParentItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = requireActivity().findViewById(R.id.settings_recycler)
        recycler.layoutManager = LinearLayoutManager(context)

        addItems()

        val adapter = ParentAdapter({
                title  ->
            when (title) {
                "Написать отзыв" -> findNavController().navigate(R.id.action_accountFragment_to_reviewCreationFragment)
            }
        }, recyclerList)
        recycler.adapter = adapter
    }

    private fun addItems(){
        recyclerList.clear()
        val settingsList = ArrayList<ChildItem>()
        settingsList.add(ChildItem("Записи на замер", R.drawable.baseline_arrow_forward_ios_24))
        settingsList.add(ChildItem("Написать отзыв", R.drawable.baseline_arrow_forward_ios_24))
        settingsList.add(ChildItem("мои отзывы", R.drawable.baseline_arrow_forward_ios_24))
        recyclerList.add(ParentItem("Возможности", settingsList))
        val settingsList2 = ArrayList<ChildItem>()
        settingsList2.add(ChildItem("О компании", R.drawable.baseline_arrow_forward_ios_24))
        settingsList2.add(ChildItem("Контаты", R.drawable.baseline_arrow_forward_ios_24))
        settingsList2.add(ChildItem("О приложении", R.drawable.baseline_arrow_forward_ios_24))
        settingsList2.add(ChildItem("Выйти из аккаунта", R.drawable.baseline_arrow_forward_ios_24))
        recyclerList.add(ParentItem("Дополнительно", settingsList2))
    }

}