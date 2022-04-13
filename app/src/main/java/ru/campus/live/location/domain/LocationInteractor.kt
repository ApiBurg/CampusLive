package ru.campus.live.location.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.location.data.model.LocationDataObject
import ru.campus.live.location.data.repository.ILocationRepository
import javax.inject.Inject

class LocationInteractor @Inject constructor(private val repository: ILocationRepository) {

    fun search(name: String?): List<LocationDataObject> {
        val result = repository.get(name)
        if (result is ResponseObject.Success)
            return result.data
        else
            return emptyList()
    }

    fun rating(id: Int) {
        repository.ratingUp(id)
    }

    fun saveLocationData(data: LocationDataObject) {
        repository.save(data)
    }

}