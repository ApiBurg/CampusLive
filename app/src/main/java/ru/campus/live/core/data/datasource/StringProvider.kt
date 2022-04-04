package ru.campus.live.core.data.datasource

import android.content.Context
import javax.inject.Inject

class StringProvider @Inject constructor(
    private val context: Context
) {

    fun get(id: Int): String = context.getString(id)

}