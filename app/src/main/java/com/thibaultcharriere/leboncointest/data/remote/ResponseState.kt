package com.thibaultcharriere.leboncointest.data.remote

sealed class ResponseState<out T : Any>

data class Success<out T : Any>(val data: T) : ResponseState<T>()

open class Error(val message: String?) : ResponseState<Nothing>()

class HttpError(message: String, val code: Int) : Error(message = message)
