package ru.campus.live.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.data.model.ErrorObject
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.domain.UploadMediaInteractor
import ru.campus.live.core.wrapper.SingleLiveEvent
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.data.model.PublicationPostObject
import ru.campus.live.feed.domain.CreatePublicationInteractor
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.data.model.UploadMediaObject
import javax.inject.Inject

class CreatePublicationViewModel @Inject constructor(
    private val interactor: CreatePublicationInteractor,
    private val uploadMediaInteractor: UploadMediaInteractor
) : ViewModel() {

    private val successLiveData = SingleLiveEvent<FeedObject>()
    private val failureLiveData = SingleLiveEvent<ErrorObject>()
    private val uploadLiveData = MutableLiveData<ArrayList<UploadMediaObject>>()
    fun onSuccessEvent() = successLiveData
    fun onFailureEvent() = failureLiveData
    fun onUploadLiveData(): LiveData<ArrayList<UploadMediaObject>> = uploadLiveData

    fun post(params: PublicationPostObject) {
        viewModelScope.launch(Dispatchers.IO) {
            var attachment = 0
            uploadLiveData.value?.let { model -> attachment = model[0].id }
            params.attachment = attachment
            when (val result = interactor.post(params)) {
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
            model.add(uploadMediaInteractor.addList(params = params))
            withContext(Dispatchers.Main) {
                uploadLiveData.value = model
            }

            val result = uploadMediaInteractor.upload(params = params)
            model.clear()
            model.add(result)
            withContext(Dispatchers.Main) {
                uploadLiveData.value = model
            }
        }
    }

    fun clearMediaList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = uploadMediaInteractor.mediaRemove()
            withContext(Dispatchers.Main) {
                uploadLiveData.value = result
            }
        }
    }

}