package ru.campus.live.core.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.UploadResultObject
import ru.campus.live.gallery.data.model.GalleryDataObject

interface IUploadMediaRepository {
    fun upload(params: GalleryDataObject): ResponseObject<UploadResultObject>
}