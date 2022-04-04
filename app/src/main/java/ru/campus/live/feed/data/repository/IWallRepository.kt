package ru.campus.live.feed.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.data.model.PublicationPostObject
import ru.campus.live.feed.db.Publication

interface IWallRepository {
    fun getCache(): List<Publication>
    fun addCache(params: ArrayList<Publication>)
    fun get(offset: Int): ResponseObject<ArrayList<FeedObject>>
    fun post(postObject: PublicationPostObject): ResponseObject<FeedObject>
    fun vote(voteObject: VoteObject)
    fun complaint(id: Int)
}