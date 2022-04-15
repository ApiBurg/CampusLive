package ru.campus.live.start.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.campus.live.core.data.model.ErrorObject
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.di.Dispatchers
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.start.data.model.LoginModel
import ru.campus.live.start.data.model.StartModel
import ru.campus.live.start.domain.IStartInteractor
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val dispatchers: Dispatchers,
    private val interactor: IStartInteractor
) : ViewModel() {

    private val listLiveData = MutableLiveData<ArrayList<StartModel>>()
    private val success = SingleLiveEvent<LoginModel>()
    private val failure = SingleLiveEvent<ErrorObject>()
    fun getListLiveData(): LiveData<ArrayList<StartModel>> = listLiveData
    fun onSuccess(): LiveData<LoginModel> = success
    fun onFailure(): LiveData<ErrorObject> = failure

    fun start() {
        viewModelScope.launch(dispatchers.io()) {
            val result = interactor.start()
            listLiveData.postValue(result)
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun login() {
        viewModelScope.launch(dispatchers.io()) {
            when (val result = interactor.login()) {
                is ResponseObject.Success -> success.postValue(result.data)
                is ResponseObject.Failure -> failure.postValue(result.errorObject)
            }
        }
    }

}
