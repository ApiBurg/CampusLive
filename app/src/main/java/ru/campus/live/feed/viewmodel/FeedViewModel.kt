package ru.campus.live.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.core.wrapper.SingleLiveEvent
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.domain.FeedInteractor
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val interactor: FeedInteractor
) : ViewModel() {

    private val _liveData = MutableLiveData<ArrayList<FeedObject>>()
    private val _onCommentStartViewEvent = SingleLiveEvent<FeedObject>()
    private val _complaintEvent = SingleLiveEvent<FeedObject>()

    fun liveData(): LiveData<ArrayList<FeedObject>> = _liveData
    fun complaintEvent() = _complaintEvent
    fun onCommentStartViewEvent() = _onCommentStartViewEvent

    init {
        get()
    }

    private fun get() {
        viewModelScope.launch(Dispatchers.IO) {
            val model = ArrayList<FeedObject>()
            _liveData.value?.let { model.addAll(it) }
            when (val result = interactor.get(model)) {
                is ResponseObject.Success -> {
                    val response = interactor.setHeader(result.data)
                    withContext(Dispatchers.Main) {
                        _liveData.value = response
                    }
                }
            }
        }
    }

    fun insert(item: FeedObject) {
        viewModelScope.launch(Dispatchers.IO) {
            val model = _liveData.value!!
            model.add(index = 1, item)
            withContext(Dispatchers.Main) {
                _liveData.value = model
            }
        }
    }

    fun complaint(item: FeedObject) {
        _complaintEvent.value = item
    }

    fun complaintSendDataOnServer(item: FeedObject) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.complaint(item)
        }
    }

    fun vote(item: FeedObject, vote: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val voteObject = VoteObject(item.id, vote)
            val result = interactor.renderVoteView(_liveData.value!!, voteObject)
            withContext(Dispatchers.Main) {
                _liveData.value = result
            }
            interactor.vote(voteObject)
        }
    }

    fun comments(item: FeedObject) {
        _onCommentStartViewEvent.value = item
    }

}