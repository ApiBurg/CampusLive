package ru.campus.live.feed.domain.usecase

import org.junit.Assert
import org.junit.Test
import ru.campus.live.feed.data.model.FeedObject

class FeedOffsetUseCaseTest {

    @Test
    fun `counts the number of records with type 1`() {
        val model = ArrayList<FeedObject>()
        model.add(FeedObject(viewType = 0))
        model.add(FeedObject(viewType = 1))
        model.add(FeedObject(viewType = 1))

        val actual = FeedOffsetUseCase().execute(model)
        val expected = 2
        Assert.assertEquals(expected, actual)
    }

}