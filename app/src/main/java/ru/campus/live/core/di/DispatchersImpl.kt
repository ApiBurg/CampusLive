package ru.campus.live.core.di

import kotlinx.coroutines.CoroutineDispatcher
import ru.campus.live.core.di.Dispatchers

class DispatchersImpl : Dispatchers {

    override fun main(): CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Main
    override fun io(): CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO

}