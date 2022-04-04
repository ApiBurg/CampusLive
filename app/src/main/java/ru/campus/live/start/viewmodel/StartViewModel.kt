package ru.campus.live.start.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.ErrorObject
import ru.campus.live.core.wrapper.SingleLiveEvent
import ru.campus.live.start.data.model.RegistrationDataObject
import ru.campus.live.start.data.model.StartDataObject
import ru.campus.live.start.domain.StartInteractor
import javax.inject.Inject

class StartViewModel @Inject constructor(private val interactor: StartInteractor) : ViewModel() {

    private val _liveData = MutableLiveData<ArrayList<StartDataObject>>()
    private val _successEvent = SingleLiveEvent<RegistrationDataObject>()
    private val _failureEvent = SingleLiveEvent<ErrorObject>()
    fun liveData(): LiveData<ArrayList<StartDataObject>> = _liveData
    fun successEvent() = _successEvent
    fun failureEvent() = _failureEvent

    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = interactor.start()
            withContext(Dispatchers.Main) {
                _liveData.value = result
            }
        }
    }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = interactor.registration()) {
                is ResponseObject.Success -> {
                    withContext(Dispatchers.Main) {
                        _successEvent.value = result.data!!
                    }
                }
                is ResponseObject.Failure -> {
                    withContext(Dispatchers.Main) {
                        _failureEvent.value = result.errorObject
                    }
                }
            }
        }
    }

}