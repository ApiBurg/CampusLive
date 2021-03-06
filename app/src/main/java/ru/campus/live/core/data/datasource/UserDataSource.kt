package ru.campus.live.core.data.datasource

import android.content.Context
import android.content.SharedPreferences
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.start.data.model.LoginModel
import javax.inject.Inject

class UserDataSource @Inject constructor(context: Context) : IUserDataSource {

    private val sPref: SharedPreferences =
        context.getSharedPreferences("AppDB", Context.MODE_PRIVATE)

    override fun login(data: LoginModel): Boolean {
        try {
            val dateReg = System.currentTimeMillis() / 1000
            with(sPref.edit()) {
                putInt("UID", data.uid)
                putString("TOKEN", data.token)
                putInt("RATING", data.rating)
                putLong("DATE_REG", dateReg)
                apply()
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun isAuth(): Boolean {
        val token = sPref.getString("TOKEN", "").toString()
        val locationId = sPref.getInt("LOCATION_ID", 0)
        if (token.isEmpty()) return false
        if (locationId == 0) return false
        return true
    }

    override fun token(): String {
        return sPref.getString("TOKEN", "").toString()
    }

    override fun uid(): Int {
        return sPref.getInt("UID", 0)
    }

    override fun locationSave(model: LocationModel) {
        with(sPref.edit()) {
            putInt("LOCATION_ID", model.id)
            putString("LOCATION_NAME", model.name)
            putString("LOCATION_ADDRESS", model.address)
            putInt("LOCATION_TYPE", model.type)
            apply()
        }
    }

    override fun location(): LocationModel {
        val id = sPref.getInt("LOCATION_ID", 0)
        val name = sPref.getString("LOCATION_NAME", "")
        val address = sPref.getString("LOCATION_ADDRESS", "")
        val type = sPref.getInt("LOCATION_TYPE", 1)
        return LocationModel(id = id, name = name!!, address = address!!, type = type)
    }

    override fun saveUserAvatarIcon(userAvatarIcon: Int) {
        sPref.edit().putInt("USER_AVATAR", userAvatarIcon).apply()
    }

    override fun getUserAvatarIcon(): Int {
        return sPref.getInt("USER_AVATAR", 0)
    }

}