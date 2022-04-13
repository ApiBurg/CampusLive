package ru.campus.live.discussion.domain

import ru.campus.live.R
import ru.campus.live.core.data.datasource.DisplayMetrics
import ru.campus.live.core.data.datasource.HostDataSource
import ru.campus.live.core.data.datasource.IUserDataSource
import ru.campus.live.core.data.datasource.StringProvider
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.discussion.data.model.DiscussionObject
import ru.campus.live.discussion.data.model.DiscussionViewType
import ru.campus.live.discussion.data.repository.IDiscussionRepository
import ru.campus.live.discussion.domain.usecase.DiscussionTitleUseCase
import ru.campus.live.discussion.domain.usecase.DiscussionVoteUseCase
import ru.campus.live.discussion.domain.usecase.UserAvatarUseCase
import ru.campus.live.feed.data.model.FeedObject
import javax.inject.Inject

class DiscussionInteractor @Inject constructor(
    private val repository: IDiscussionRepository,
    private val titleUseCase: DiscussionTitleUseCase,
    private val userDataSource: IUserDataSource,
    private val hostDataSource: HostDataSource,
    private val display: DisplayMetrics,
    private val stringProvider: StringProvider
) {

    fun get(publicationId: Int): ResponseObject<ArrayList<DiscussionObject>> {
        return repository.get(publicationId)
    }

    fun shimmer(): ArrayList<DiscussionObject> {
        val model = ArrayList<DiscussionObject>()
        model.add(DiscussionObject(DiscussionViewType.PARENT_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.PARENT_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.PARENT_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        return model
    }

    fun refreshUserAvatar(model: ArrayList<DiscussionObject>) {
        userDataSource.saveUserAvatarIcon(UserAvatarUseCase().execute(model, userDataSource.uid()))
    }

    fun commentsCount(model: ArrayList<DiscussionObject>?): Int {
        var count = 0
        model?.forEach { item ->
            if (item.type == DiscussionViewType.PARENT
                || item.type == DiscussionViewType.CHILD
            ) count++
        }
        return count
    }

    fun getTitle(size: Int): String {
        return titleUseCase.execute(size)
    }

    fun vote(params: VoteObject) {
        repository.vote(params.id, params.vote)
    }

    fun renderVoteView(
        model: ArrayList<DiscussionObject>,
        voteObject: VoteObject
    ): ArrayList<DiscussionObject> {
        return DiscussionVoteUseCase().execute(model, voteObject)
    }

    fun complaint(id: Int) {
        repository.complaint(id)
    }

    fun header(
        model: ArrayList<DiscussionObject>,
        publication: FeedObject
    ): ArrayList<DiscussionObject> {
        if (model.size == 0) {
            return mapper(model, publication)
        } else if (model[0].type != DiscussionViewType.PUBLICATION) {
            return mapper(model, publication)
        }
        return model
    }

    fun setTypeObject(model: ArrayList<DiscussionObject>): ArrayList<DiscussionObject> {
        val response = ArrayList<DiscussionObject>()
        model.forEach { item ->
            if (item.type != DiscussionViewType.PUBLICATION) {
                if (item.parent == 0) {
                    item.type = DiscussionViewType.PARENT
                    response.add(item)
                }

                model.forEach { child ->
                    if (child.parent == item.id) {
                        child.type = DiscussionViewType.CHILD
                        response.add(child)
                    }
                }
            } else {
                response.add(item)
            }
        }
        return listPreparation(response)
    }

    private fun listPreparation(model: ArrayList<DiscussionObject>): ArrayList<DiscussionObject> {
        model.forEachIndexed { index, item ->
            if (item.type == DiscussionViewType.PARENT || item.type == DiscussionViewType.CHILD) {
                var pathUserIcon = hostDataSource.domain() + "media/icon/" + item.icon_id + ".png"
                if (item.icon_id < 10) pathUserIcon =
                    hostDataSource.domain() + "media/icon/" + item.icon_id + ".png"
                model[index].userAvatar = pathUserIcon

                if (item.attachment != null) {
                    val params = display.get(item.attachment.width, item.attachment.height)
                    model[index].mediaWidth = params[0]
                    model[index].mediaHeight = params[1]
                } else {
                    model[index].mediaWidth = 0
                    model[index].mediaHeight = 1
                }

                if (item.hidden == 1)
                    model[index].message = stringProvider.get(R.string.comment_hidden)
            }
        }
        return model
    }

    private fun mapper(
        model: ArrayList<DiscussionObject>,
        publication: FeedObject
    ): ArrayList<DiscussionObject> {
        model.add(
            0, DiscussionObject(
                type = DiscussionViewType.PUBLICATION,
                id = publication.id,
                hidden = 1,
                author = 0,
                icon_id = 0,
                message = publication.message,
                attachment = publication.attachment,
                rating = publication.rating,
                vote = publication.vote,
                parent = 0,
                answered = 0,
                relativeTime = publication.relativeTime
            )
        )
        return model
    }

}