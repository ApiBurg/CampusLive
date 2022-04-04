package ru.campus.live.core.domain

import ru.campus.live.core.data.model.ItemVoteDataObject
import ru.campus.live.core.data.model.VoteObject

private const val NOT_VOTE = 0
private const val LIKE = 1
private const val DISLIKE = 2

class ItemVoteEditUseCase {

    fun execute(item: ItemVoteDataObject, voteObject: VoteObject): ItemVoteDataObject {
        val rating = item.rating
        when (item.vote) {
            NOT_VOTE -> {
                if (voteObject.vote == LIKE) {
                    item.rating = rating + 1
                    item.vote = LIKE
                } else {
                    item.rating = rating - 1
                    item.vote = DISLIKE
                }
            }
            LIKE -> {
                if (voteObject.vote == LIKE) {
                    item.rating = rating - 1
                    item.vote = NOT_VOTE
                } else {
                    item.rating = rating - 2
                    item.vote = DISLIKE
                }
            }
            DISLIKE -> {
                if (voteObject.vote == DISLIKE) {
                    item.rating = rating + 1
                    item.vote = NOT_VOTE
                } else {
                    item.rating = rating + 2
                    item.vote = LIKE
                }
            }
        }
        return item
    }

}