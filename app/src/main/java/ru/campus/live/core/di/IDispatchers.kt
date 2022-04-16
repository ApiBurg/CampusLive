package ru.campus.live.core.di

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}