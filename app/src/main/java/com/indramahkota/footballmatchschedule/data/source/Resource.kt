package com.indramahkota.footballmatchschedule.data.source

class Resource<T> private constructor(
    val status: Status,
    val data: T?
) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(data: T?): Resource<T?> {
            return Resource(Status.ERROR, data)
        }

        fun <T> loading(data: T?): Resource<T?> {
            return Resource(Status.LOADING, data)
        }
    }
}