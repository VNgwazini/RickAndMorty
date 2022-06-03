package com.example.simplemorty

import retrofit2.Response

data class SimpleResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception : Exception?
){
    companion object{
        fun <T> success(data: Response<T>): SimpleResponse<T> {
            return SimpleResponse(
                status = Status.Success,
                data = data,
                exception = null
            )
        }
        fun <T> failure(exception: Exception): SimpleResponse<T> {
            return SimpleResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }
    }
    //similar to enum
    sealed class Status{
        object Success: Status()
        object Failure: Status()
    }
    //single expression function equivalent to: get() { return this.status == Status.Failure }
    val failed: Boolean get() = this.status == Status.Failure
    //did not fail and nullable data object is not null
    val isSuccessful : Boolean get() = !failed && this.data?.isSuccessful == true
    //if data or body is null, get will fail and throw null pointer exception
    val body: T get() = this.data!!.body()!!
}
