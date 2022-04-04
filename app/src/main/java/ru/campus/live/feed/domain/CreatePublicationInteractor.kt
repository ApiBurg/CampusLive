package ru.campus.live.feed.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.data.model.PublicationPostObject
import ru.campus.live.feed.data.repository.IWallRepository
import javax.inject.Inject

class CreatePublicationInteractor @Inject constructor(
    private val repository: IWallRepository
) {

    fun post(params: PublicationPostObject): ResponseObject<FeedObject> {
        return repository.post(params)
    }
}