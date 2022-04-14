package ru.campus.live.core.di

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatchers {
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
}