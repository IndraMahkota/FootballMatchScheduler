package com.indramahkota.footballapp.data.source.remote.model

import com.google.gson.annotations.SerializedName
import com.indramahkota.footballapp.data.source.remote.model.ClassementModel

data class ClassementResponse(
    @SerializedName("table")
    val table: List<ClassementModel>? = null
)