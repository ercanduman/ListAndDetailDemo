package ercanduman.listanddetaildemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ercanduman.listanddetaildemo.data.repository.AppRepository
import ercanduman.listanddetaildemo.util.DataResult
import kotlinx.coroutines.launch

/**
 * A ViewModel is responsible for preparing and managing the data for an Activity or a Fragment.
 * ViewModel will not be destroyed if its owner is destroyed for a configuration
 * change (e.g. rotation). The new owner instance just re-connects to the existing model.
 *
 * @author ercanduman
 * @since  25.03.2021
 */
class MainViewModel(private val repository: AppRepository) : ViewModel() {

    private val _dataResult = MutableLiveData<DataResult>()
    val dataResult: LiveData<DataResult> = _dataResult

    fun getItems() = viewModelScope.launch {
        _dataResult.value = DataResult.Loading
        _dataResult.value = repository.getItems()
    }
}