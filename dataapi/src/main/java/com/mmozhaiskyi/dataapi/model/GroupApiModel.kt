package com.mmozhaiskyi.dataapi.model

import com.google.gson.annotations.SerializedName
import com.mmozhaiskyi.model.Group

data class GroupApiModel(
    @SerializedName("group_id") val id: String,
    @SerializedName("group_full_name") val name: String,
    @SerializedName("group_okr") val okr: String,
    @SerializedName("group_type") val type: String) {

    fun toModel() = Group(id, name, okr, type)
}
