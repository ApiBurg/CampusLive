package ru.campus.live.feed.domain

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import ru.campus.live.core.data.datasource.IUserDataSource
import ru.campus.live.feed.data.model.FeedObject
import ru.campus.live.feed.data.repository.IWallRepository
import ru.campus.live.location.data.model.LocationDataObject

class FeedInteractorTest {

    private val repository = mock<IWallRepository>()
    private val userDataSource = mock<IUserDataSource>()

    @Test
    fun `the first in the list should be the heading (type 0)`() {
        val model = ArrayList<FeedObject>()
        model.add(FeedObject(viewType = 1))

        val locationDataObject = LocationDataObject(1, "name", "address", 1)
        Mockito.`when`(userDataSource.location()).thenReturn(locationDataObject)

        val interactor = FeedInteractor(repository, userDataSource)
        val actualModel = interactor.setHeader(model)

        val expected = 0
        val actual = actualModel[0].viewType
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun removeItem() {
        val model = ArrayList<FeedObject>()
        model.add(FeedObject(id = 1))
        model.add(FeedObject(id = 2))
        model.add(FeedObject(id = 3))

        val interactor = FeedInteractor(repository, userDataSource)
        val actualModel = interactor.removeItem(1, model)

        val expected = 2
        val actual = actualModel[0].id

        Assert.assertEquals(expected, actual)
    }


}