package com.har.habitforyou.`object`

class Result<T> {

    var code: Int = 0
    var message: String = ""
    var data: T? = null


    companion object {
        @JvmOverloads
        fun <T> createSuccessResult(data: T? = null): Result<T> {
            val newResult: Result<T> = Result()
            newResult.code = 0
            newResult.message = ""
            newResult.data = data
            return newResult
        }

        @JvmOverloads
        fun <T> createResult(code: Int = 0, message: String = "", data: T? = null): Result<T> {
            val newResult: Result<T> = Result()
            newResult.code = code
            newResult.message = message
            newResult.data = data
            return newResult
        }
    }
}