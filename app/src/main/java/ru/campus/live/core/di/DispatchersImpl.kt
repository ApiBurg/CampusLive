package ru.campus.live.core.di

import kotlinx.coroutines.CoroutineDispatcher
import ru.campus.live.core.di.Dispatchers

class DispatchersImpl : Dispatchers {

    override val MAIN: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.Main

    override val IO: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.IO
}