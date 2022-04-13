package ru.campus.live.start.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.start.data.model.RegistrationDataObject
import ru.campus.live.start.data.model.StartDataObject
import ru.campus.live.start.data.repository.IStartRepository
import javax.inject.Inject

class StartInteractor @Inject constructor(private val repository: IStartRepository) {

    fun start(): ArrayList<StartDataObject> {
        return repository.start()
    }

    fun registration(): ResponseObject<RegistrationDataObject> {
        val result = repository.registration()
        if (result is ResponseObject.Success<RegistrationDataObject>)
            repository.login(result.data)
        return result
    }

}