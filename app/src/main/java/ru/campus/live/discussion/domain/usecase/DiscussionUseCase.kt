package ru.campus.live.discussion.domain.usecase

import ru.campus.live.discussion.data.repository.IDiscussionRepository

class DiscussionUseCase(private val repositoryI: IDiscussionRepository) {

    /*
    fun execute(publication: FeedObject): ArrayList<DiscussionObject> {
        val response = oldRepository.execute(publication.id)
        return if (response.resultType == ResultType.SUCCESS) {
            val model = response.data!!.commentItemTypeMapper()
            model.add(0, publicationMapper(publication))
            model
        } else {
            val model = ArrayList<DiscussionObject>()
            model.add(0, publicationMapper(publication))
            model
        }
    }

    fun title(model: ArrayList<DiscussionObject>): String {
        var count = 0
        model.forEach { item ->
            if (item.type == 1 || item.type == 2) count++
        }
        return titleUseCase.execute(count)
    }

    private fun ArrayList<DiscussionObject>.commentItemTypeMapper(): ArrayList<DiscussionObject> {
        val model = ArrayList<DiscussionObject>()
        this.forEach { item ->
            if (item.parent == 0) {
                item.type = 1
                model.add(item)
            }
            this.forEach { child ->
                if (child.parent == item.id) {
                    child.type = 2
                    model.add(child)
                }
            }
        }
        return model
    }

    private fun publicationMapper(publication: FeedObject): DiscussionObject {
        return DiscussionObject(
            type = 3,
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
    }


     */

}