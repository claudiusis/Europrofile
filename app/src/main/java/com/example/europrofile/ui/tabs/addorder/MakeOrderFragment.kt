package com.example.europrofile.ui.tabs.addorder

import android.os.Bundle
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.europrofile.data.RequestResult
import com.example.europrofile.databinding.FragmentMakeOrderBinding
import com.example.europrofile.domain.Order
import com.example.europrofile.ui.accountpages.profile.ProfileViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class MakeOrderFragment : Fragment() {

    private lateinit var binding : FragmentMakeOrderBinding

    private val userViewModel : ProfileViewModel by activityViewModels()
    private val orderViewModel : OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMakeOrderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = (userViewModel.userInfo.value as RequestResult.Success).data

        user.let {
            binding.editCreationOrder.setText(user.name)
            binding.editCreationOrder2.setText(user.number)
            binding.editCreationOrder3.setText(user.email)
            binding.editTextAddress.setText(user.address)
        }

        val listOfDate = listOf(binding.editCreationOrder4, binding.imgCreationOrder4, binding.editSubtitle4)

        listOfDate.forEach {

            it.setOnClickListener {

                val constraintsBuilder = CalendarConstraints.Builder().setValidator(object : CalendarConstraints.DateValidator {
                    override fun describeContents(): Int {
                        return -1
                    }

                    override fun writeToParcel(dest: Parcel, flags: Int) {}

                    override fun isValid(date: Long): Boolean {
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = date

                        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                            return false
                        }

                        val today = Calendar.getInstance()
                        today.set(Calendar.HOUR_OF_DAY, 0)
                        today.set(Calendar.MINUTE, 0)
                        today.set(Calendar.SECOND, 0)
                        today.set(Calendar.MILLISECOND, 0)

                        return calendar.timeInMillis >= today.timeInMillis
                    }
                }).build()

                val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText(
                    "Выберите подходящую дату"
                ).setTextInputFormat(SimpleDateFormat("dd.MM.yyyy")).setCalendarConstraints(constraintsBuilder)
                    .build()

                datePicker.addOnPositiveButtonClickListener { selection ->

                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendar.timeInMillis = selection

                    val format = SimpleDateFormat("dd.MM.yyyy")
                    val date = format.format(calendar.time)

                    binding.editCreationOrder4.text = date
                }

                datePicker.show(childFragmentManager, "SHOW")
            }
        }

        val listOfTime = listOf(binding.editCreationOrder5, binding.imgCreationOrder5, binding.editSubtitle5)

        listOfTime.forEach {
            it.setOnClickListener {

                val timePickerDialog = MaterialTimePicker.Builder().setTimeFormat(
                    TimeFormat.CLOCK_24H
                ).setHour(12).setMinute(0).setTitleText("Выберите время").build()

                timePickerDialog.addOnPositiveButtonClickListener {
                    val minute = if (timePickerDialog.minute.toString().length!=2) "0${timePickerDialog.minute}" else timePickerDialog.minute.toString()
                    val time = timePickerDialog.hour.toString() + ":" + minute
                    binding.editCreationOrder5.text = time
                }

                timePickerDialog.show(childFragmentManager, "SHOW")

            }
        }

        binding.button.setOnClickListener {
            val order = Order(
                user.id+user.countOfOrders,
                user.id,
                binding.editCreationOrder.text.toString(),
                binding.editCreationOrder2.text.toString(),
                binding.editCreationOrder3.text.toString(),
                binding.editCreationOrder4.text.toString(),
                binding.editCreationOrder5.text.toString(),
                binding.editTextAddress.text.toString(),
                binding.editCreationOrder6.text.toString(),
                Date()
            )

            orderViewModel.makeOrder(order)
            findNavController().popBackStack()
        }

        binding.arrowImg.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}