package ru.campus.live.start.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.start.data.model.LoginModel
import ru.campus.live.start.data.model.StartModel
import ru.campus.live.start.data.repository.IStartRepository
import javax.inject.Inject

interface IStartInteractor {
    fun start(): ArrayList<StartModel>
    fun login(): ResponseObject<LoginModel>
}

class StartInteractor @Inject constructor(
    private val repositoryI: IStartRepository,
) : IStartInteractor {

    override fun start(): ArrayList<StartModel> {
        return repositoryI.start()
    }

    override fun login(): ResponseObject<LoginModel> {
        val result = repositoryI.registration()
        if (result is ResponseObject.Success<LoginModel>)
            repositoryI.login(result.data)
        return result
    }

}
