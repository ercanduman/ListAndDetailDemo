package ercanduman.listanddetaildemo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ercanduman.listanddetaildemo.data.repository.AppRepository

/**
 * Utility class to provide instance of ViewModel for a scope of Activity or Fragment.
 *
 * @author ercanduman
 * @since  29.04.2021
 */
class MainViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        } else throw IllegalArgumentException("Unknown ViewModel class found as ${modelClass.name}")
    }
}