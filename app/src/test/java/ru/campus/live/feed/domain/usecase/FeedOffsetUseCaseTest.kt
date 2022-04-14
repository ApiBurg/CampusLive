package ru.campus.live.feed.domain.usecase

import org.junit.Assert
import org.junit.Test
import ru.campus.live.feed.data.model.FeedModel
import ru.campus.live.feed.data.model.FeedViewType

class FeedOffsetUseCaseTest {

    @Test
    fun `counts the number of records with type FeedViewType = PUBLICATION`() {
        val model = ArrayList<FeedModel>()
        model.add(FeedModel(viewType = FeedViewType.HEADING))
        model.add(FeedModel(viewType = FeedViewType.PUBLICATION))
        model.add(FeedModel(viewType = FeedViewType.PUBLICATION))

        val actual = FeedOffsetUseCase().execute(model)
        val expected = 2
        Assert.assertEquals(expected, actual)
    }

}