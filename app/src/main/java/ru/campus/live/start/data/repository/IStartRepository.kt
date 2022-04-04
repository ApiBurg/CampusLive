package ru.campus.live.start.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.start.data.model.RegistrationDataObject
import ru.campus.live.start.data.model.StartDataObject

interface IStartRepository {
    fun start(): ArrayList<StartDataObject>
    fun registration(): ResponseObject<RegistrationDataObject>
    fun login(data: RegistrationDataObject): Boolean
}