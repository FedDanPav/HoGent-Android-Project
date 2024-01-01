package com.androidproject.util

/**
 * A generic class that holds a value with its loading status.
 * @param T the type of the value.
 * @property data the value.
 * @property message the error message.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>(null)
}
