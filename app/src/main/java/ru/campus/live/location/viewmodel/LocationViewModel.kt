package ru.campus.live.location.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.di.Dispatchers
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.location.domain.LocationInteractor
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val dispatcher: Dispatchers,
    private val interactor: LocationInteractor,
) : ViewModel() {

    private val listLiveData = MutableLiveData<List<LocationModel>>()
    private val success = SingleLiveEvent<LocationModel>()
    fun liveData(): LiveData<List<LocationModel>> = listLiveData
    fun onSuccess(): LiveData<LocationModel> = success

    fun search(name: String? = null) {
        viewModelScope.launch(dispatcher.io()) {
            val result = interactor.search(name)
            withContext(dispatcher.main()) {
                listLiveData.value = result
            }
        }
    }

    fun save(item: LocationModel) {
        viewModelScope.launch(dispatcher.io()) {
            interactor.rating(item.id)
            interactor.saveLocationData(item)
            success.postValue(item)
        }
    }

}