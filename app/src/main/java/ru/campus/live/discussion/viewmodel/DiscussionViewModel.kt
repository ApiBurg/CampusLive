package ru.campus.live.discussion.viewmodel

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
import ru.campus.live.discussion.data.model.DiscussionObject
import ru.campus.live.discussion.data.model.DiscussionViewType
import ru.campus.live.discussion.domain.DiscussionInteractor
import ru.campus.live.feed.data.model.FeedObject
import javax.inject.Inject


class DiscussionViewModel @Inject constructor(
    private val interactor: DiscussionInteractor
) : ViewModel() {

    private var publication: FeedObject? = null
    private val _liveData = MutableLiveData<ArrayList<DiscussionObject>>()
    private val titleLiveData = MutableLiveData<String>()
    private val _complaintEvent = SingleLiveEvent<DiscussionObject>()
    fun liveData(): LiveData<ArrayList<DiscussionObject>> = _liveData
    fun getTitleLiveData(): LiveData<String> = titleLiveData
    fun complaintEvent() = _complaintEvent

    fun get(params: FeedObject?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (publication == null) publication = params
            val model = ArrayList<DiscussionObject>()
            _liveData.value?.let { model.addAll(it) }
            val start = interactor.header(model, publication!!)
            if(params!!.comments != 0) {
                val shimmer = interactor.shimmer()
                start.addAll(shimmer)
            }

            withContext(Dispatchers.Main) {
                _liveData.value = start
            }

            when (val result = interactor.get(params.id)) {
                is ResponseObject.Success -> {
                    val response = interactor.setTypeObject(result.data)
                    val final = interactor.header(response, publication!!)
                    withContext(Dispatchers.Main) {
                        _liveData.value = final
                    }
                }
                is ResponseObject.Failure -> {
                    val response = ArrayList<DiscussionObject>()
                    response.add(DiscussionObject(DiscussionViewType.DISCUSSION_NONE))
                    val final = interactor.header(response, publication!!)
                    withContext(Dispatchers.Main) {
                        _liveData.value = final
                    }
                }
            }

            interactor.refreshUserAvatar(_liveData.value!!)
            title()
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = interactor.get(publication?.id ?: 0)
            if (result is ResponseObject.Success) {
                val model = ArrayList<DiscussionObject>()
                _liveData.value?.let { model.add(it[0]) }
                val response = interactor.setTypeObject(result.data)
                model.addAll(response)
                withContext(Dispatchers.Main) {
                    _liveData.value = model
                }
                title()
            } else {
                _liveData.postValue(_liveData.value!!)
            }
        }
    }

    fun insert(item: DiscussionObject) {
        viewModelScope.launch(Dispatchers.IO) {
            val model = _liveData.value
            model!!.add(item)
            val result = interactor.setTypeObject(model)
            withContext(Dispatchers.Main) {
                _liveData.value = result
            }
        }
    }

    fun title() {
        viewModelScope.launch(Dispatchers.IO) {
            val count = interactor.commentsCount(_liveData.value)
            val result = interactor.getTitle(count)
            withContext(Dispatchers.Main) {
                titleLiveData.value = result
            }
        }
    }

    fun vote(item: DiscussionObject, vote: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val voteObject = VoteObject(id = item.id, vote = vote)
            val result = interactor.renderVoteView(_liveData.value!!, voteObject)
            withContext(Dispatchers.Main) {
                _liveData.value = result
            }
            interactor.vote(voteObject)
        }
    }

    fun complaint(item: DiscussionObject) {
        _complaintEvent.value = item
        viewModelScope.launch(Dispatchers.IO) {
            interactor.complaint(item.id)
        }
    }

}