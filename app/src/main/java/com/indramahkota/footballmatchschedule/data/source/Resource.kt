package com.indramahkota.footballmatchschedule.data.source

class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {
    val isSuccess: Boolean
        get() = status === Status.SUCCESS && data != null

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T?): Resource<T?> {
            return Resource(Status.ERROR, data, msg)
        }
    }
}