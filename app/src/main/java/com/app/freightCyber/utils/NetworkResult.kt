package com.app.freightCyber.utils


sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Throwable) : NetworkResult<Nothing>()
    data class Loading(val message : String? = null) : NetworkResult<Nothing>()

}

