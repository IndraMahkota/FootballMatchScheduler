package com.indramahkota.footballapp.data.source.remote.apiresponse

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballapp.data.source.remote.apimodel.ClassementApiModel

data class ClassementApiResponse(
    @SerializedName("table")
    val table: List<ClassementApiModel>? = null
)