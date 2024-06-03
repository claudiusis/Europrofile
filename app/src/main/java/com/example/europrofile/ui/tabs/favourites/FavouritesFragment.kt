package com.example.europrofile.ui.tabs.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.europrofile.R
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentFavouritesBinding
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import com.example.europrofile.ui.detailspage.DetailsViewModel
import com.example.europrofile.ui.tabs.main.condition.ConditionChildAdapter
import com.example.europrofile.ui.tabs.main.condition.ConditionerViewModel


class FavouritesFragment : Fragment() {

    private lateinit var binding : FragmentFavouritesBinding
    private val favouritesViewModel : ConditionerViewModel by activityViewModels()
    private val detailsViewModel : DetailsViewModel by activityViewModels()
    private val user : ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ConditionChildAdapter(favouritesViewModel.favourites.value?: listOf(), { link, imgList ->
            detailsViewModel.getData(link, imgList)
            findNavController().navigate(R.id.action_favouritesFragment_to_detailsConditionerFragment2)
        }, { elem ->
            val localUser = (user.userInfo.value as RequestResult.Success).data
            localUser.listOfFavourites.remove(elem.title)
            user.changeInfo(localUser, "Likes")
        }, {
            image, conditioner ->
            image.background = ContextCompat.getDrawable(requireContext(), R.drawable.favourite_fill_icon)
        })

        binding.favourRv.adapter = adapter
        binding.favourRv.layoutManager = GridLayoutManager(requireContext(), 2)

        user.userInfo.observe(viewLifecycleOwner){
            if (it is RequestResult.Success) {
                favouritesViewModel.refactorFavourites(it.data)
            }
        }

        favouritesViewModel.favourites.observe(viewLifecycleOwner){
            adapter.refactorList(it)
        }

    }

}