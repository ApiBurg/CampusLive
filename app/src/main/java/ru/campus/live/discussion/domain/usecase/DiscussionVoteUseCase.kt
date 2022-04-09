package ru.campus.live.discussion.domain.usecase

import ru.campus.live.core.data.model.ItemVoteDataObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.core.domain.ItemVoteEditUseCase
import ru.campus.live.discussion.data.model.DiscussionObject

class DiscussionVoteUseCase {

    fun execute(
        model: ArrayList<DiscussionObject>,
        voteObject: VoteObject
    ): ArrayList<DiscussionObject> {
        val index = model.indexOfFirst { it.id == voteObject.id }
        val rating = model[index].rating

        val itemVoteDataObject = ItemVoteDataObject(rating = rating, vote = model[index].vote)
        val newItemVoteDataObject = ItemVoteEditUseCase().execute(itemVoteDataObject, voteObject)

        val item = model[index]
        val newItem = item.copy(
            type = item.type,
            id = item.id,
            hidden = item.hidden,
            author = item.author,
            icon_id = item.icon_id,
            message = item.message,
            attachment = item.attachment,
            rating = newItemVoteDataObject.rating,
            vote = newItemVoteDataObject.vote,
            parent = item.parent,
            answered = item.answered,
            relativeTime = item.relativeTime
        )

        model.removeAt(index)
        model.add(index, newItem)
        return model
    }
}