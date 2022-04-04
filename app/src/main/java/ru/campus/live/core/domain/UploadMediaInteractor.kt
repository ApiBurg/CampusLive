package ru.campus.live.core.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.UploadResultObject
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.data.model.UploadMediaObject
import javax.inject.Inject

class UploadMediaInteractor @Inject constructor(
    private val uploadRepository: IUploadMediaRepository
) {

    fun addList(params: GalleryDataObject): UploadMediaObject {
        return UploadMediaObject(
            id = 0,
            fullPath = params.fullPath,
            upload = true,
            error = false,
            animation = false
        )
    }

    fun upload(params: GalleryDataObject): UploadMediaObject {
        when (val result = uploadRepository.upload(params)) {
            is ResponseObject.Success<UploadResultObject> -> {
                return UploadMediaObject(
                    id = result.data.id,
                    fullPath = params.fullPath,
                    upload = false,
                    error = false,
                    animation = false
                )
            }
            is ResponseObject.Failure<UploadResultObject> -> {
                return UploadMediaObject(
                    id = 0,
                    fullPath = params.fullPath,
                    upload = false,
                    error = true,
                    animation = false
                )
            }
        }
    }

    fun mediaRemove(): ArrayList<UploadMediaObject> {
        return ArrayList()
    }

}