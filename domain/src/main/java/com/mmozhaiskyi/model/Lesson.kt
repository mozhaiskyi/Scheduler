package com.mmozhaiskyi.model

data class Lesson(
    val id: String,
    val dayNumber: Short,
    val day: String,
    val name: String,
    val fullName: String,
    val lessonNumber: Short,
    val type: String,
    val weekNumber: Short,
    val timeStart: String,
    val timeEnd: String,
    val rate: Int,
    val teachers: List<Teacher>,
    val groups: List<Group>,
    val rooms: List<Room>)
