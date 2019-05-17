package com.mmozhaiskyi.domain

import com.mmozhaiskyi.model.Group
import com.mmozhaiskyi.model.Lesson
import com.mmozhaiskyi.model.Teacher
val teachers = listOf(
    Teacher(
        "1",
        "Ivanov Ivan Ivanovich",
        "",
        "",
        2.0
    ),
    Teacher(
        "2",
        "Petrov P. P.",
        "Petrov Petro Petrovich",
        "",
        3.0
    )
)

val groups = listOf(
    Group(
        "1",
        "ti-51",
        "bak",
        "day"
    ),
    Group(
        "2",
        "to-41",
        "bak",
        ""
    ),
    Group(
        "3",
        "pi-71",
        "mas",
        "day"
    )
)

val lessons = listOf(
    Lesson(
        "1",
        3,
        "Sunday",
        "Something like a shit",
        "",
        3,
        "Workshop",
        1,
        "12.00.00",
        "13.40.00",
        1,
        listOf(),
        listOf(),
        listOf()
    )
)
