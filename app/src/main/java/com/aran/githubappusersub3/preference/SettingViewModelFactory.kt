package com.aran.githubappusersub3.preference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aran.githubappusersub3.viewmodel.SettingsViewModel

class SettingViewModelFactory(private val pref: SharedPref) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}