package ru.campus.live.start.data.repository

import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.CloudDataSource
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.start.data.model.OSType
import ru.campus.live.start.data.model.RegistrationDataObject
import ru.campus.live.start.data.model.StartDataObject
import javax.inject.Inject

class StartRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val userDataSource: UserDataSource,
    private val dataSource: PresentationDataSource,
) : IStartRepository {

    override fun start(): ArrayList<StartDataObject> {
        return dataSource.execute()
    }

    override fun registration(): ResponseObject<RegistrationDataObject> {
        val call = apiService.registration(os = OSType.ANDROID.ordinal)
        return CloudDataSource<RegistrationDataObject>(errorDataSource = errorDataSource)
            .execute(call)
    }

    override fun login(data: RegistrationDataObject): Boolean {
        return userDataSource.login(data)
    }

}