package com.har.onecommitaday.api.model

sealed class ApiResponse<out T : Any, out U : Any> {

    companion object {
        fun create(throwable: Throwable): Error {
            return Error(Error.Unknown, throwable.message ?: "Unknown Error")
        }

        fun create(code: Int, message: String): Error {
            return Error(code, message)
        }

        fun <T: Any> create(body: T): Success<T> {
            return Success(body)
        }
    }

    data class Success<T: Any>(val body: T) : ApiResponse<T, Nothing>()

    data class ApiError<U: Any>(val body: U, val code: Int) : ApiResponse<Nothing, U>()

    data class Error(val code: Int = Unknown, val message: String): ApiResponse<Nothing, Nothing>() {
        companion object {
            const val Unknown: Int = 520
            const val NoContent: Int = 204
        }
    }
}