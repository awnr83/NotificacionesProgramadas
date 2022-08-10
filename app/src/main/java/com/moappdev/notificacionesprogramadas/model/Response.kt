package com.moappdev.notificacionesprogramadas.model

sealed class Response<out T> {
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data:T
    ):Response<T>()

    data class Faliure(
        val message: String
    ):Response<Nothing>()
}