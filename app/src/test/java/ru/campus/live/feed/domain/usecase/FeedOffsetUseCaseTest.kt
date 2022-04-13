package ru.campus.live.feed.domain.usecase

import org.junit.Assert
import org.junit.Test
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.data.model.FeedViewType

class FeedOffsetUseCaseTest {

    @Test
    fun `counts the number of records with type FeedViewType = PUBLICATION`() {
        val model = ArrayList<FeedObject>()
        model.add(FeedObject(viewType = FeedViewType.HEADING))
        model.add(FeedObject(viewType = FeedViewType.PUBLICATION))
        model.add(FeedObject(viewType = FeedViewType.PUBLICATION))

        val actual = FeedOffsetUseCase().execute(model)
        val expected = 2
        Assert.assertEquals(expected, actual)
    }

}