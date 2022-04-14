package ru.campus.live.location.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.location.data.model.LocationDataObject
import ru.campus.live.location.domain.LocationInteractor
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val interactor: LocationInteractor,
) : ViewModel() {

    private val _liveData = MutableLiveData<List<LocationDataObject>>()
    private val _feedStartEvent = SingleLiveEvent<LocationDataObject>()
    fun liveData() = _liveData
    fun feedStartEvent() = _feedStartEvent

    fun search(name: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = interactor.search(name)
            withContext(Dispatchers.Main) {
                _liveData.value = result
            }
        }
    }

    fun locationSave(item: LocationDataObject) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.rating(item.id)
            interactor.saveLocationData(item)
            _feedStartEvent.postValue(item)
        }
    }

}