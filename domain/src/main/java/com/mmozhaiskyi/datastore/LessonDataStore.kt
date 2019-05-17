package com.mmozhaiskyi.datastore

import com.mmozhaiskyi.model.Lesson
import io.reactivex.Completable
import io.reactivex.Observable

interface LessonDataStore {

    interface Remote {

        fun getLessonsByTeacher(teacherId: String): Observable<List<Lesson>>

        fun getLessonsByGroup(groupId: String): Observable<List<Lesson>>
    }

    interface Cache {

        fun getLessonsByTeacher(teacherId: String): Observable<List<Lesson>>

        fun getLessonsByGroup(groupId: String): Observable<List<Lesson>>

        fun deleteLessonsByTeacher(teacherId: String): Completable

        fun deleteLessonsByGroup(groupId: String): Completable

        fun saveLessons(lessons: List<Lesson>): Completable
    }
}
