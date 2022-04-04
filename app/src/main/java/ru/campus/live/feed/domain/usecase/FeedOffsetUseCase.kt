package ru.campus.live.feed.domain.usecase

import ru.campus.live.feed.data.model.FeedObject

class FeedOffsetUseCase {

    fun execute(model: ArrayList<FeedObject>): Int {
        var offset = 0
        model.forEach { item -> if (item.type == 1) offset++ }
        return offset
    }

}