package ru.campus.live.core.data.datasource

import ru.campus.live.location.data.model.LocationDataObject
import ru.campus.live.start.data.model.LoginModel

interface IUserDataSource {
    fun login(data: LoginModel): Boolean
    fun isAuth(): Boolean
    fun token(): String
    fun uid(): Int
    fun locationSave(dataObject: LocationDataObject)
    fun location(): LocationDataObject
    fun saveUserAvatarIcon(userAvatarIcon: Int)
    fun getUserAvatarIcon(): Int
}