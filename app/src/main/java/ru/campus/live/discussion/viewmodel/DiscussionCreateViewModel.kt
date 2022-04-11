package ru.campus.live.discussion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.data.model.ErrorObject
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.wrapper.SingleLiveEvent
import ru.campus.live.discussion.data.model.CommentCreateObject
import ru.campus.live.discussion.data.model.DiscussionObject
import ru.campus.live.discussion.domain.CreateCommentInteractor
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.data.model.UploadMediaObject
import javax.inject.Inject

class DiscussionCreateViewModel @Inject constructor(
    private val commentInteractor: CreateCommentInteractor
) : ViewModel() {

    private val successLiveData = SingleLiveEvent<DiscussionObject>()
    private val failureLiveData = SingleLiveEvent<ErrorObject>()
    private val uploadLiveData = MutableLiveData<ArrayList<UploadMediaObject>>()
    fun onSuccessEvent() = successLiveData
    fun onFailureEvent() = failureLiveData
    fun onUploadLiveData(): LiveData<ArrayList<UploadMediaObject>> = uploadLiveData

    fun post(params: CommentCreateObject) {
        var upload = 0
        uploadLiveData.value?.let { model -> upload = model[0].id }
        params.attachment = upload
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = commentInteractor.post(params)) {
                is ResponseObject.Success -> {
                    withContext(Dispatchers.Main) {
                        successLiveData.value = result.data!!
                    }
                }
                is ResponseObject.Failure -> {
                    withContext(Dispatchers.Main) {
                        failureLiveData.value = result.errorObject
                    }
                }
            }
        }
    }

    fun upload(params: GalleryDataObject) {
        viewModelScope.launch(Dispatchers.IO) {
            val model = ArrayList<UploadMediaObject>()
            model.add(commentInteractor.uploadMediaMapper(params = params))
            withContext(Dispatchers.Main) {
                uploadLiveData.value = model
            }

            val result = commentInteractor.upload(params = params)
            model.clear()
            model.add(result)
            withContext(Dispatchers.Main) {
                uploadLiveData.value = model
            }
        }
    }

    fun clearMediaUpload() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = commentInteractor.clearUploadMedia()
            withContext(Dispatchers.Main) {
                uploadLiveData.value = result
            }
        }
    }

}