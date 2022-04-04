package ru.campus.live.gallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.data.repository.IGalleryRepository
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val repository: IGalleryRepository
) : ViewModel() {

    private val _liveData = MutableLiveData<ArrayList<GalleryDataObject>>()
    fun liveData(): LiveData<ArrayList<GalleryDataObject>> = _liveData

    fun execute() {
        viewModelScope.launch(Dispatchers.IO) {
            val model = ArrayList<GalleryDataObject>()
            _liveData.value?.let { model.addAll(it) }
            val response = repository.get(model.size)
            if (response.size != 0) model.addAll(response)
            withContext(Dispatchers.Main) {
                _liveData.value = model
            }
        }
    }

}