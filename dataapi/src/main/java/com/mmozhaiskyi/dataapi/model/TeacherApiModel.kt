package com.mmozhaiskyi.dataapi.model

import com.google.gson.annotations.SerializedName
import com.mmozhaiskyi.model.Teacher

data class TeacherApiModel(
    @SerializedName("teacher_id") val id: String,
    @SerializedName("teacher_name") val name: String,
    @SerializedName("teacher_full_name") val fullName: String?,
    @SerializedName("teacher_short_name") val shortName: String?,
    @SerializedName("teacher_rating") val rating: Double) {

    fun toModel() = Teacher(id, name, fullName ?: "", shortName ?: "", rating)
}
