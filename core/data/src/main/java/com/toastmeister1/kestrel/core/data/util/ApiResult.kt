package com.toastmeister1.kestrel.core.data.util

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>

    data class Error(val code: Int, val message: String, val body: String) : ApiResult<Nothing>

    data class Exception(val throwable: Throwable) : ApiResult<Nothing>
}