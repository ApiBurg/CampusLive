package ru.campus.live.ribbon.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.di.IDispatchers
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.domain.RibbonInteractor
import javax.inject.Inject

class RibbonViewModel @Inject constructor(
    private val dispatchers: IDispatchers,
    private val interactor: RibbonInteractor
) : ViewModel() {

    private val listLiveData = MutableLiveData<ArrayList<RibbonModel>>()
    val list: LiveData<ArrayList<RibbonModel>>
        get() = listLiveData

    private val startDiscussionEvent = SingleLiveEvent<RibbonModel>()
    val startDiscussion: LiveData<RibbonModel>
        get() = startDiscussionEvent

    private val complaintEvent = SingleLiveEvent<RibbonModel>()
    val complaint: LiveData<RibbonModel>
        get() = complaintEvent

    fun get(refresh: Boolean = false) {
        viewModelScope.launch(dispatchers.io) {
            val model = ArrayList<RibbonModel>()
            if (!refresh) list.value?.let { model.addAll(it) }
            val result = interactor.get(model)
            withContext(dispatchers.main) {
                listLiveData.value = result
            }
        }
    }

}