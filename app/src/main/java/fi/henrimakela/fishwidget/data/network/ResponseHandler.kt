package fi.henrimakela.fishwidget.data.network

import fi.henrimakela.domain.Resource
import retrofit2.HttpException
import java.net.SocketTimeoutException

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error("Network error", null)
            is SocketTimeoutException -> Resource.error("Connection timeout", null)
            else -> Resource.error("Something went wrong. Please check your internet connection", null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            401 -> "Unauthorised"
            404 -> "Not found"
            408 -> "Timeout"
            else -> "Something went wrong"
        }
    }
}