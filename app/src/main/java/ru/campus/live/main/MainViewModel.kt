package ru.campus.live.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.campus.live.core.wrapper.SingleLiveEvent
import ru.campus.live.core.data.datasource.UserDataSource
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userDataSource: UserDataSource) : ViewModel() {

    private val _authEvent = SingleLiveEvent<Boolean>()
    fun authEvent() = _authEvent

    init {
        isAuth()
    }

    private fun isAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            val auth = userDataSource.isAuth()
            Log.d("MyLog", "Статус авторизации = $auth")
            if (auth) {
                withContext(Dispatchers.Main) {
                    _authEvent.value = true
                }
            }
        }
    }

}