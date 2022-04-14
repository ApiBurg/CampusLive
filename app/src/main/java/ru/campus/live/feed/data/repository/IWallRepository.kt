package ru.campus.live.feed.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.feed.data.model.FeedModel
import ru.campus.live.feed.data.model.PublicationPostObject
import ru.campus.live.feed.db.Publication

interface IWallRepository {
    fun getCache(): List<Publication>
    fun addCache(params: ArrayList<Publication>)
    fun get(offset: Int): ResponseObject<ArrayList<FeedModel>>
    fun post(postObject: PublicationPostObject): ResponseObject<FeedModel>
    fun vote(voteObject: VoteObject)
    fun complaint(id: Int)
}