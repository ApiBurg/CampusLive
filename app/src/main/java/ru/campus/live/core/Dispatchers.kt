package ru.campus.live.core

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatchers {
    val MAIN: CoroutineDispatcher
    val IO: CoroutineDispatcher
}