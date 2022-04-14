package ru.campus.live.feed.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.feed.data.model.FeedModel
import ru.campus.live.feed.data.model.PublicationPostObject
import ru.campus.live.feed.data.repository.IWallRepository
import javax.inject.Inject

class CreatePublicationInteractor @Inject constructor(
    private val repository: IWallRepository
) {

    fun post(params: PublicationPostObject): ResponseObject<FeedModel> {
        return repository.post(params)
    }
}