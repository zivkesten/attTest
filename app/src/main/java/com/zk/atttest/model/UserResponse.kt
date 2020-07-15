package com.zk.atttest.model

import com.google.gson.annotations.SerializedName

data class UsersResponse (
    @SerializedName("results")
    var results: List<Item> = emptyList(),
    @SerializedName("info")
    var info: Info? = null,

    //Error pattern
    @SerializedName("Error") val errorMessage: String? = null
)

class Info {
    @SerializedName("seed")
    var seed: String? = null
    @SerializedName("results")
    var results: Int? = 0
    @SerializedName("page")
    var page: Int? = 0
    @SerializedName("version")
    var version: String? = null
}
