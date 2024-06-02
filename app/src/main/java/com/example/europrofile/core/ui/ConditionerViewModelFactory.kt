package com.example.europrofile.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.europrofile.domain.ConditionInfo
import com.example.europrofile.domain.User
import com.example.europrofile.ui.tabs.main.condition.ConditionerViewModel
import javax.inject.Inject

class ConditionerViewModelFactory @Inject  constructor(private val rep : ConditionInfo, val user: User) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConditionerViewModel::class.java)) {
            return ConditionerViewModel(rep, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}