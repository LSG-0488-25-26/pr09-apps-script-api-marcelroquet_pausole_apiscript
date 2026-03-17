package com.example.apiscript.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apiscript.sharedPreferences.SettingsRepository

class LaptopViewModelFactory(private val repository: SettingsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LaptopViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LaptopViewModel(repository) as T
        }
        throw IllegalArgumentException("NO ES TROBA EL VM")
    }
}