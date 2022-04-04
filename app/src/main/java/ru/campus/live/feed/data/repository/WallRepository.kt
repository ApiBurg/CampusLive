package ru.campus.live.feed.data.repository

import okhttp3.ResponseBody
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.CloudDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.data.model.PublicationPostObject

private const val OBJECT_TYPE = 1

class WallRepository(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val user: UserDataSource
) : IWallRepository {

    override fun get(offset: Int): ResponseObject<ArrayList<FeedObject>> {
        val call = apiService.wallGet(user.token(), user.location().id, offset)
        return CloudDataSource<ArrayList<FeedObject>>(errorDataSource = errorDataSource)
            .execute(call)
    }

    override fun post(postObject: PublicationPostObject): ResponseObject<FeedObject> {
        val call = apiService.post(
            user.token(), user.location().id,
            postObject.message, postObject.attachment
        )
        return CloudDataSource<FeedObject>(errorDataSource = errorDataSource).execute(call)
    }

    override fun vote(voteObject: VoteObject) {
        val call = apiService.publicationVote(
            token = user.token(), vote = voteObject.vote,
            objectId = voteObject.id
        )
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

    override fun complaint(id: Int) {
        val call = apiService.complaint(OBJECT_TYPE, id)
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

}