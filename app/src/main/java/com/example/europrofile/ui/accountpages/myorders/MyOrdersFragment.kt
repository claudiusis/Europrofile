package com.example.europrofile.ui.accountpages.myorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentMyOrdersBinding
import com.example.europrofile.ui.accountpages.myorders.recycler.OrderAdapter
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import com.example.europrofile.ui.tabs.addorder.OrderViewModel

class MyOrdersFragment : Fragment() {

    private lateinit var binding : FragmentMyOrdersBinding

    private val orderViewModel : OrderViewModel by activityViewModels()
    private val user : ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyOrdersBinding.inflate(inflater)

        orderViewModel.getOrders((user.userInfo.value as RequestResult.Success).data.id)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerMyOrders.adapter = OrderAdapter(mutableListOf())
        binding.recyclerMyOrders.layoutManager = LinearLayoutManager(requireContext())

        orderViewModel.orders.observe(viewLifecycleOwner){
            (binding.recyclerMyOrders.adapter as OrderAdapter).refreshList(it)
        }

        orderViewModel.statusOfLoad.observe(viewLifecycleOwner) {
            binding.emptyImg.visibility = View.GONE
            binding.emptyText.visibility = View.GONE
            when(it){

                is RequestResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is RequestResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
                is RequestResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (orderViewModel.orders.value?.isEmpty() != false){
                        binding.emptyImg.visibility = View.VISIBLE
                        binding.emptyText.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

}