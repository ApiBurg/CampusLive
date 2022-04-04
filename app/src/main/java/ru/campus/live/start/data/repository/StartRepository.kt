package ru.campus.live.start.data.repository

import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.CloudDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.StringProvider
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.start.data.model.RegistrationDataObject
import ru.campus.live.start.data.model.StartDataObject

private const val DEVICE_OS = 1

class StartRepository(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val userDataSource: UserDataSource,
    private val stringProvider: StringProvider
) : IStartRepository {

    override fun start(): ArrayList<StartDataObject> {
        return StartDataSource(stringProvider).execute()
    }

    override fun registration(): ResponseObject<RegistrationDataObject> {
        val call = apiService.registration(DEVICE_OS)
        return CloudDataSource<RegistrationDataObject>(errorDataSource = errorDataSource).execute(call)
    }

    override fun login(data: RegistrationDataObject): Boolean {
        return userDataSource.login(data)
    }

}