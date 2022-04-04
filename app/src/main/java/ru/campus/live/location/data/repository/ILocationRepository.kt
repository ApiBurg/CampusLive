package ru.campus.live.location.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.location.data.model.LocationDataObject

interface ILocationRepository {
    fun get(name: String?): ResponseObject<List<LocationDataObject>>
    fun ratingUp(id: Int)
    fun save(data: LocationDataObject)
}