package ru.campus.live.feed.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.core.di.IDispatchers
import ru.campus.live.core.presentation.wrapper.SingleLiveEvent
import ru.campus.live.feed.data.model.FeedModel
import ru.campus.live.feed.domain.FeedInteractor
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val dispatchers: IDispatchers,
    private val interactor: FeedInteractor
) : ViewModel() {

    private var isLazyLoad = false

    private val listLiveData = MutableLiveData<ArrayList<FeedModel>>()
    val list: LiveData<ArrayList<FeedModel>>
        get() = listLiveData

    private val startDiscussionEvent = SingleLiveEvent<FeedModel>()
    val startDiscussion: LiveData<FeedModel>
        get() = startDiscussionEvent

    private val complaintEvent = SingleLiveEvent<FeedModel>()
    val complaint: LiveData<FeedModel>
        get() = complaintEvent


    fun getCache() {
        viewModelScope.launch(Dispatchers.IO) {
            /* val result = interactor.getCache()
             if (result.size != 0) {
                 withContext(Dispatchers.Main) {
                     _liveData.value = result
                 }
             }

             */
        }
    }

    fun get(refresh: Boolean = false) {
        if (isLazyLoad) return
        viewModelScope.launch(dispatchers.io) {
            isLazyLoad = true
            val model = ArrayList<FeedModel>()
            if (!refresh) listLiveData.value?.let { model.addAll(it) }
            when (val result = interactor.get(model)) {
                is ResponseObject.Success -> {
                    model.addAll(result.data)
                    val list = interactor.setHeader(model)
                    val response = interactor.listPreparation(list)
                    withContext(dispatchers.main) {
                        listLiveData.value = response
                        insertCache()
                    }
                }
                is ResponseObject.Failure -> {
                    if (model.size == 0) {
                        val response = interactor.setHeader(model)
                        withContext(dispatchers.main) {
                            listLiveData.value = response
                        }
                    }
                }
            }
            isLazyLoad = false
        }
    }

    private fun insertCache() {
        viewModelScope.launch(Dispatchers.IO) {
            val model = ArrayList<FeedModel>()
            listLiveData.value?.let { model.addAll(it) }
            if (model.size < 27) interactor.insertCache(model)
        }
    }

    fun insert(item: FeedModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val model = listLiveData.value!!
            model.add(index = 1, item)
            withContext(Dispatchers.Main) {
                listLiveData.value = model
            }
        }
    }

    fun complaint(item: FeedModel) {
        complaintEvent.value = item
    }

    fun comments(item: FeedModel) {
        startDiscussionEvent.value = item
    }

    fun complaintSendDataOnServer(item: FeedModel) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.complaint(item)
        }
    }

    fun vote(item: FeedModel, vote: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val voteObject = VoteObject(id = item.id, vote = vote)
            val result = interactor.renderVoteView(listLiveData.value!!, voteObject)
            withContext(Dispatchers.Main) {
                listLiveData.value = result
            }
            interactor.vote(voteObject)
        }
    }

}