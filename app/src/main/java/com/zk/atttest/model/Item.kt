package com.zk.atttest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Item (
    @SerializedName("gender")
    var gender: String? = null,
    @SerializedName("name")
    var name: Name? = null,
    @SerializedName("location")
    var location: Location? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("cell")
    var cell: String? = null,
    @SerializedName("picture")
    var picture: Picture? = null
): Parcelable

fun Item.address(): String{
    val addressBuilder = StringBuilder()
    //Build the address
    addressBuilder.append(location?.street?.number)
        .append(" ")
        .append(location?.street?.name)
        .append(", ")
        .append(location?.city)
        .append(", ")
        .append(location?.country)
    return addressBuilder.toString()
}

fun Item.name(): String {
    val nameBuilder = StringBuilder()
    nameBuilder.append(name?.first)
        .append(" ")
        .append(name?.last)
    return nameBuilder.toString()
}

@Parcelize
data class Name (
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("first")
    var first: String? = null,
    @SerializedName("last")
    var last: String? = null
): Parcelable

@Parcelize
data class Location (
    @SerializedName("street")
    var street: Street? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("state")
    var state: String? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("postcode")
    var postcode: String? = null
): Parcelable

@Parcelize
data class Street (
    @SerializedName("number")
    var number: Int? = 0,
    @SerializedName("name")
    var name: String? = null
): Parcelable

@Parcelize
data class Picture (
    @SerializedName("large")
    var large: String? = null,
    @SerializedName("medium")
    var medium: String? = null,
    @SerializedName("thumbnail")
    var thumbnail: String? = null
): Parcelable
