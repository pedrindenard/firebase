package com.pdm.firebase.arquitecture

import android.util.Log
import com.google.gson.Gson
import com.pdm.firebase.util.ERROR_SERVER
import retrofit2.Response

class Event {

    companion object {

        inline fun <T> safeCallApi(apiCall: () -> Resource<T>): Resource<T> {
            return try {
                apiCall()
            } catch (e: Throwable) {
                Log.e(ERROR_SERVER, e.toString())
                Resource.Failure(e)
            }
        }

        fun <T> Response<T>.errorCallApi(apiInvalidToken: () -> Resource<T>): Resource<T> {
            return when (this.code()) {
                404 or 422 or 422 -> {
                    Resource.Error(
                        code = 404,
                        message = this.message()
                    )
                }
                500 or 501 or 503 -> {
                    Resource.Error(
                        code = 500,
                        message = this.message()
                    )
                }
                400 or 401 or 403 -> {
                    apiInvalidToken()
                }
                else -> {
                    Resource.Error(
                        code = this.code(),
                        message = this.message()
                    )
                }
            }
        }

        fun adapterClick(onClick: () -> Unit) = try { onClick() } catch (e: Exception) { }

        fun Any?.toJson(): String = Gson().toJson(this)
    }
}