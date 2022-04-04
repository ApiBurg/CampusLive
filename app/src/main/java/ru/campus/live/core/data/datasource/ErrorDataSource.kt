package ru.campus.live.core.data.datasource

import ru.campus.live.R
import ru.campus.live.core.data.model.ErrorObject
import javax.inject.Inject

class ErrorDataSource @Inject constructor(private val stringProvider: StringProvider) {

    fun get(code: Int = 0): ErrorObject {
        return when (code) {
            0 -> ErrorObject(code, R.drawable.neural, stringProvider.get(R.string.error_network))
            else -> ErrorObject(code, R.drawable.error, stringProvider.get(R.string.error_unknown))
        }
    }

}