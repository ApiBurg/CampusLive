package ru.campus.live.feed.domain.usecase

import ru.campus.live.core.data.model.ItemVoteDataObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.core.domain.ItemVoteEditUseCase
import ru.campus.live.feed.data.model.FeedObject

class FeedVoteUseCase {

    private var rating = 0

    fun execute(model: ArrayList<FeedObject>, voteObject: VoteObject): ArrayList<FeedObject> {
        val index = model.indexOfFirst { it.id == voteObject.id }
        rating = model[index].rating

        val itemVoteDataObject = ItemVoteDataObject(rating, voteObject.vote)
        val newItemVoteDataObject = ItemVoteEditUseCase().execute(itemVoteDataObject, voteObject)

        val item = model[index]
        val newItem = item.copy(
            type = item.type,
            location = item.location,
            id = item.id,
            message = item.message,
            attachment = item.attachment,
            rating = newItemVoteDataObject.rating,
            comments = item.comments,
            vote = newItemVoteDataObject.vote,
            relativeTime = item.relativeTime
        )
        model.removeAt(index)
        model.add(index, newItem)
        return model
    }

}