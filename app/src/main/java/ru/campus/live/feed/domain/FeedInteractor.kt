package ru.campus.live.feed.domain

import ru.campus.live.core.data.datasource.interfaces.IUserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.UploadResultObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.data.model.PublicationPostObject
import ru.campus.live.feed.data.repository.IWallRepository
import ru.campus.live.feed.domain.usecase.FeedOffsetUseCase
import ru.campus.live.feed.domain.usecase.FeedVoteUseCase
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.data.model.UploadMediaObject
import ru.campus.live.location.data.model.LocationDataObject

class FeedInteractor(
    private val repository: IWallRepository,
    private val userDataSource: IUserDataSource,
    private val uploadRepository: IUploadMediaRepository
) {

    fun get(model: ArrayList<FeedObject>): ResponseObject<ArrayList<FeedObject>> {
        val offset = FeedOffsetUseCase().execute(model)
        return repository.get(offset)
    }

    fun setHeader(model: ArrayList<FeedObject>): ArrayList<FeedObject> {
        val location = LocationDataObject(
            id = userDataSource.location().id,
            name = userDataSource.location().name,
            address = userDataSource.location().name,
            type = userDataSource.location().type
        )
        model.add(0, FeedObject(type = 0, location = location))
        return model
    }

    fun complaint(item: FeedObject) {
        repository.complaint(item.id)
    }

    fun vote(voteObject: VoteObject) {
        repository.vote(voteObject)
    }

    fun renderVoteView(
        model: ArrayList<FeedObject>,
        voteObject: VoteObject
    ): ArrayList<FeedObject> {
        return FeedVoteUseCase().execute(model, voteObject)
    }

    fun removeItem(id: Int, model: ArrayList<FeedObject>): ArrayList<FeedObject> {
        val index = model.indexOfLast { it.id == id }
        model.removeAt(index)
        return model
    }

    fun post(params: PublicationPostObject): ResponseObject<FeedObject> {
        return repository.post(params)
    }

    fun merge(params: FeedObject, model: ArrayList<FeedObject>): ArrayList<FeedObject> {
        model.add(params)
        return model
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

}