package ru.campus.live.start.domain

import org.junit.After
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import ru.campus.live.core.data.model.ErrorObject
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.start.data.model.LoginModel
import ru.campus.live.start.data.repository.IStartRepository

class StartInteractorTest {

    private val startRepository = mock<IStartRepository>()

    @After
    fun tearDown() {
        Mockito.reset(startRepository)
    }

    @Test
    fun `the start method calls the repository`() {
        Mockito.`when`(startRepository.start()).thenReturn(ArrayList())
        val interactor = StartInteractor(repository = startRepository)

        interactor.start()
        Mockito.verify(startRepository, Mockito.times(1)).start()
    }

    @Test
    fun `upon successful registration, call login`() {
        val response =
            ResponseObject.Success(LoginModel(uid = 1, token = "", rating = 15))
        Mockito.`when`(startRepository.registration()).thenReturn(response)
        val interactor = StartInteractor(repository = startRepository)

        interactor.login()
        Mockito.verify(startRepository, Mockito.times(1)).login(response.data)
    }

    @Test
    fun `login failed, do not call login method`() {
        val response = ResponseObject.Failure<LoginModel>(
            ErrorObject(code = 0, icon = 1, message = "")
        )
        val registrationDataObject = LoginModel(uid = 1, token = "", rating = 15)
        Mockito.`when`(startRepository.registration()).thenReturn(response)

        val interactor = StartInteractor(repository = startRepository)
        interactor.login()
        Mockito.verify(startRepository, Mockito.never()).login(registrationDataObject)
    }

}