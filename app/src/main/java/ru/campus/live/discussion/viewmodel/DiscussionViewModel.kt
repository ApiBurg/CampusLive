package ru.campus.live.discussion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.Dispatchers
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.core.wrapper.SingleLiveEvent
import ru.campus.live.discussion.data.model.DiscussionObject
import ru.campus.live.discussion.domain.DiscussionInteractor
import ru.campus.live.feed.data.model.FeedModel
import javax.inject.Inject

class DiscussionViewModel @Inject constructor(
    private val dispatcher: Dispatchers,
    private val interactor: DiscussionInteractor,
) : ViewModel() {

    private var publication: DiscussionObject? = null
    private val listLiveData = MutableLiveData<ArrayList<DiscussionObject>>()
    fun getListLiveData(): LiveData<ArrayList<DiscussionObject>> = listLiveData
    private val titleLiveData = MutableLiveData<String>()
    fun getTitleLiveData(): LiveData<String> = titleLiveData
    private val complaintEvent = SingleLiveEvent<DiscussionObject>()
    fun getComplaintEvent() = complaintEvent

    init {
        listLiveData.observeForever {
            title()
            refreshUserAvatar()
        }
    }

    fun set(params: FeedModel) {
        publication = interactor.map(params)
    }

    fun get() {
        viewModelScope.launch(dispatcher.IO) {
            if (listLiveData.value == null) shimmer()
            when (val result = interactor.get(publication!!.id)) {
                is ResponseObject.Success -> {
                    val response = interactor.preparation(result.data)
                    withContext(dispatcher.MAIN) {
                        listLiveData.value = response
                    }
                }
                is ResponseObject.Failure -> {
                    val response = interactor.error(publication!!)
                    withContext(dispatcher.MAIN) {
                        listLiveData.value = response
                    }
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(dispatcher.IO) {
            val publication = listLiveData.value?.get(0)!!
            when (val result = interactor.get(publication.id)) {
                is ResponseObject.Success -> {
                    val preparationList = interactor.preparation(result.data)
                    val response = interactor.header(publication, preparationList)
                    withContext(dispatcher.MAIN) {
                        listLiveData.value = response
                    }
                }
                is ResponseObject.Failure -> {
                    withContext(dispatcher.MAIN) {
                        listLiveData.value = listLiveData.value
                    }
                }
            }
        }
    }

    fun insert(item: DiscussionObject) {
        viewModelScope.launch(dispatcher.IO) {
            val result = interactor.insert(item, listLiveData.value!!)
            val response = interactor.preparation(result)
            withContext(dispatcher.MAIN) {
                listLiveData.value = response
            }
        }
    }

    fun title() {
        viewModelScope.launch(dispatcher.IO) {
            val count = interactor.count(listLiveData.value!!)
            val title = interactor.title(count)
            withContext(dispatcher.MAIN) {
                titleLiveData.value = title
            }
        }
    }

    fun vote(params: VoteObject) {
        viewModelScope.launch(dispatcher.IO) {
            val result = interactor.renderVoteView(listLiveData.value!!, params)
            withContext(dispatcher.MAIN) {
                listLiveData.value = result
            }
            interactor.vote(params)
        }
    }

    fun complaint(item: DiscussionObject) {
        complaintEvent.value = item
        viewModelScope.launch(dispatcher.IO) {
            interactor.complaint(item.id)
        }
    }

    private suspend fun shimmer() {
        val model = interactor.shimmer()
        val response = interactor.header(publication!!, model)
        withContext(dispatcher.MAIN) {
            listLiveData.value = response
        }
    }

    private fun refreshUserAvatar() {
        viewModelScope.launch(dispatcher.IO) {
            if (listLiveData.value != null) interactor.refreshUserAvatar(listLiveData.value!!)
        }
    }

    override fun onCleared() {
        listLiveData.removeObserver { }
        super.onCleared()
    }

}