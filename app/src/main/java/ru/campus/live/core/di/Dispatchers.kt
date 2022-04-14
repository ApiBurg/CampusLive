package ru.campus.live.core.di

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatchers {
    val MAIN: CoroutineDispatcher
    val IO: CoroutineDispatcher
}