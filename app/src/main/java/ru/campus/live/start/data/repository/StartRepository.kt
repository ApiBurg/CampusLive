package ru.campus.live.start.data.repository

import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.CloudDataSource
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.start.data.model.LoginModel
import ru.campus.live.start.data.model.OSType
import ru.campus.live.start.data.model.StartModel
import javax.inject.Inject

interface IStartRepository {
    fun start(): ArrayList<StartModel>
    fun registration(): ResponseObject<LoginModel>
    fun login(data: LoginModel): Boolean
}

class StartRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val userDataSource: UserDataSource,
    private val dataSource: StartDataSource
) : IStartRepository {

    override fun start(): ArrayList<StartModel> {
        return dataSource.execute()
    }

    override fun registration(): ResponseObject<LoginModel> {
        val call = apiService.registration(os = OSType.ANDROID.ordinal)
        return CloudDataSource<LoginModel>(errorDataSource = errorDataSource)
            .execute(call)
    }

    override fun login(data: LoginModel): Boolean {
        return userDataSource.login(data)
    }

}