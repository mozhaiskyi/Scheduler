package com.mmozhaiskyi.dataapi.model

import com.google.gson.annotations.SerializedName
import com.mmozhaiskyi.model.Room

data class RoomApiModel(
    @SerializedName("room_id") val id: String,
    @SerializedName("room_name") val name: String,
    @SerializedName("room_latitude") val latitude: Double,
    @SerializedName("room_longitude") val longitude: Double) {

    fun toModel() = Room(id, name, latitude, longitude)
}
