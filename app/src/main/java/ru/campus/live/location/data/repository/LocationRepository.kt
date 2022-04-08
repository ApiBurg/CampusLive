package ru.campus.live.location.data.repository

import okhttp3.ResponseBody
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.datasource.CloudDataSource
import ru.campus.live.core.data.datasource.ErrorDataSource
import ru.campus.live.core.data.datasource.UserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.location.data.model.LocationDataObject
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val user: UserDataSource
) : ILocationRepository {

    override fun get(name: String?): ResponseObject<List<LocationDataObject>> {
        val call = apiService.location(user.token(), name)
        return CloudDataSource<List<LocationDataObject>>(errorDataSource = errorDataSource)
            .execute(call)
    }

    override fun ratingUp(id: Int) {
        val call = apiService.locationRating(id, user.token())
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

    override fun save(data: LocationDataObject) {
        user.locationSave(data)
    }

}