package ru.campus.live.feed.domain.usecase

import ru.campus.live.feed.data.model.FeedModel
import ru.campus.live.feed.data.model.FeedViewType

class FeedOffsetUseCase {

    fun execute(model: ArrayList<FeedModel>): Int {
        var offset = 0
        model.forEach { item -> if (item.viewType == FeedViewType.PUBLICATION) offset++ }
        return offset
    }

}