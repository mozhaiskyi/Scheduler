package com.mmozhaiskyi.dataapi.model

import com.google.gson.annotations.SerializedName
import com.mmozhaiskyi.model.Lesson

data class LessonApiModel(
    @SerializedName("lesson_id") val id: String,
    @SerializedName("day_number") val dayNumber: Short,
    @SerializedName("day_name") val day: String,
    @SerializedName("lesson_name") val name: String,
    @SerializedName("lesson_full_name") val fullName: String,
    @SerializedName("lesson_number") val lessonNumber: Short,
    @SerializedName("lesson_type") val type: String,
    @SerializedName("lesson_week") val weekNumber: Short,
    @SerializedName("time_start") val timeStart: String,
    @SerializedName("time_end") val timeEnd: String,
    @SerializedName("rate") val rate: Int,
    val teachers: List<TeacherApiModel>?,
    val groups: List<GroupApiModel>?,
    val rooms: List<RoomApiModel>?) {

    fun toModel() = Lesson(
        id,
        dayNumber,
        day, name,
        fullName,
        lessonNumber,
        type,
        weekNumber,
        timeStart,
        timeEnd,
        rate,
        teachers = teachers?.map { it.toModel() } ?: listOf(),
        groups = groups?.map { it.toModel() } ?: listOf(),
        rooms = rooms?.map { it.toModel() } ?: listOf()
    )
}
