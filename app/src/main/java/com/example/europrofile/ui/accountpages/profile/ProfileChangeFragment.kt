package com.example.europrofile.ui.accountpages.profile

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.europrofile.MainActivity
import com.example.europrofile.R
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentProfileChangeBinding
import com.example.europrofile.domain.User

class ProfileChangeFragment : Fragment() {

    private lateinit var binding: FragmentProfileChangeBinding

    private val user : ProfileViewModel by activityViewModels()

    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileChangeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // check()

        val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                Glide.with(requireContext()).load(it).into(binding.avatarIcon)
                uri = it
                binding.iconText.visibility = View.GONE
            }
        }

        binding.avatarIcon.setOnClickListener {
            resultLauncher.launch("image/*")
        }

        user.userInfo.observe(viewLifecycleOwner){

            when (it) {
                is RequestResult.Loading ->  {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is RequestResult.Success -> {
                    val result = user.userInfo.value as RequestResult.Success
                    binding.progressBar.visibility = View.GONE

                    binding.profileName.setText(result.data.name)
                    binding.profileInfo1.setText(result.data.number)
                    binding.profileInfo2.text = result.data.email
                    binding.profileInfo3.setText(result.data.address)
                    binding.profileInfo4.text = "⚫".repeat(result.data.password.length)


                        binding.iconText.setTextColor(resources.getColor(R.color.white))
                        binding.iconText.text = if (it.data.name.split(" ").size >= 2)
                            it.data.name[0].toString() + (it.data.name.split(" ")[1][0]).toString()
                        else it.data.name[0].toString()
                }
                is RequestResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.profileName.setText((user.userInfo.value as RequestResult.Error).e.message.toString())
                }
            }

        }



        binding.button.setOnClickListener {

            val userData = (user.userInfo.value as RequestResult.Success).data

            user.changeInfo(
                User(
                    userData.id,
                    binding.profileInfo2.text.toString(),
                    binding.profileName.text.toString(),
                    binding.profileInfo1.text.toString(),
                    userData.password,
                    binding.profileInfo3.text.toString(),
                    uri.toString(),
                    userData.countOfReviews,
                    userData.countOfOrders,
                    userData.listOfFavourites
                ), ""
            )

            findNavController().popBackStack()
        }

        binding.locarion.setOnClickListener {
            getLocation()
        }



    }

    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val task = (activity as MainActivity).fusedLocationServices.lastLocation

        task.addOnSuccessListener {
            if (it!=null){
                val geocoder = Geocoder(requireContext())
                val address = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                binding.profileInfo3.setText(address!!.get(0).getAddressLine(0))
            }
        }
    }

/*    fun check() {
        val result = user.userInfo.value as RequestResult.Success
        if (!result.data.imgUri!!.isEmpty()) {
            binding.iconText.setTextColor(resources.getColor(R.color.white))
            binding.iconText.text = if (result.data.name.split(" ").size == 2)
                result.data.name[0].toString() + (result.data.name.split(" ")[1][0]).toString()
            else result.data.name[0].toString()
        } else {
            Glide.with(requireContext()).load(result.data.imgUri).into(binding.avatarIcon)
        }
    }*/


}