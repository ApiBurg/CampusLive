package ru.campus.live.core.data.model

import org.jetbrains.annotations.NotNull

sealed class ResponseObject<T> {
    class Success<T>(val data: T) : ResponseObject<T>()
    class Failure<T>(val errorObject: ErrorObject) : ResponseObject<T>()
}