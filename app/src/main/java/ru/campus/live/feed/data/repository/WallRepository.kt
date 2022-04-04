package ru.campus.live.feed.data.repository

import okhttp3.ResponseBody
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.CloudDataSource
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.data.model.PublicationPostObject
import ru.campus.live.feed.db.AppDatabase
import ru.campus.live.feed.db.Publication
import javax.inject.Inject

private const val OBJECT_TYPE = 1

class WallRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val user: UserDataSource,
    private val appDataSource: AppDatabase
) : IWallRepository {

    override fun getCache(): List<Publication> {
        val publicationDao = appDataSource.publicationDao()
        return publicationDao.get()
    }

    override fun addCache(params: ArrayList<Publication>) {
        val publicationDao = appDataSource.publicationDao()
        publicationDao.clear()
        params.forEach { item ->
            publicationDao.insert(item)
        }
    }

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