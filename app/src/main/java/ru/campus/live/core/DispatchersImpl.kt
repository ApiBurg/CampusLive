package ru.campus.live.core

import kotlinx.coroutines.CoroutineDispatcher

class DispatchersImpl : Dispatchers {

    override val MAIN: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.Main

    override val IO: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.IO
}