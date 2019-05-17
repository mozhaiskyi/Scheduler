package com.mmozhaiskyi.datastore

import com.mmozhaiskyi.model.Lesson
import io.reactivex.Completable
import io.reactivex.Single

interface LessonDataStore {

    interface Remote {

        fun getLessonsByTeacher(teacherId: String): Single<List<Lesson>>

        fun getLessonsByGroup(groupId: String): Single<List<Lesson>>
    }

    interface Cache {

        fun getLessonsByTeacher(teacherId: String): Single<List<Lesson>>

        fun getLessonsByGroup(groupId: String): Single<List<Lesson>>

        fun deleteLessonsByTeacher(teacherId: String): Completable

        fun deleteLessonsByGroup(groupId: String): Completable

        fun saveLessons(lessons: List<Lesson>): Completable
    }
}
