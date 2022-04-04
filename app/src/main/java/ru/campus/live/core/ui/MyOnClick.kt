package ru.campus.live.core.ui

import android.view.View

interface MyOnClick<T> {
    fun item(view: View, item: T)
}