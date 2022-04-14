package ru.campus.live.location.data.repository

import okhttp3.ResponseBody
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.CloudDataSource
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.location.data.model.LocationModel
import javax.inject.Inject

interface ILocationRepository {
    fun get(name: String?): ResponseObject<List<LocationModel>>
    fun ratingUp(id: Int)
    fun save(data: LocationModel)
}

class LocationRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val userDataSource: UserDataSource
) : ILocationRepository {

    override fun get(name: String?): ResponseObject<List<LocationModel>> {
        val call = apiService.location(userDataSource.token(), name)
        return CloudDataSource<List<LocationModel>>(errorDataSource = errorDataSource)
            .execute(call)
    }

    override fun ratingUp(id: Int) {
        val call = apiService.locationRating(id, userDataSource.token())
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

    override fun save(data: LocationModel) {
        userDataSource.locationSave(data)
    }

}