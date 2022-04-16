package ru.campus.live.ribbon.domain.usecase

import ru.campus.live.core.data.model.ItemVoteDataObject
import ru.campus.live.core.data.model.VoteObject
import ru.campus.live.core.domain.ItemVoteEditUseCase
import ru.campus.live.ribbon.data.model.RibbonModel

class FeedVoteUseCase {

    fun execute(model: ArrayList<RibbonModel>, voteObject: VoteObject): ArrayList<RibbonModel> {
        val index = model.indexOfFirst { it.id == voteObject.id }
        val rating = model[index].rating

        val itemVoteDataObject = ItemVoteDataObject(rating = rating, vote = model[index].vote)
        val newItemVoteDataObject = ItemVoteEditUseCase().execute(itemVoteDataObject, voteObject)

        val item = model[index]
        val newItem = item.copy(
            viewType = item.viewType,
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