package com.mmozhaiskyi.datadb.datastore

import com.mmozhaiskyi.datastore.LessonDataStore
import com.mmozhaiskyi.model.Lesson
import io.reactivex.Completable
import io.reactivex.Observable

internal class LessonDataStoreImpl : LessonDataStore.Cache {

    override fun getLessonsByTeacher(teacherId: String): Observable<List<Lesson>> {

    }

    override fun getLessonsByGroup(groupId: String): Observable<List<Lesson>> {

    }

    override fun deleteLessonsByTeacher(teacherId: String): Completable {
    }

    override fun deleteLessonsByGroup(groupId: String): Completable {
    }

    override fun saveLessons(lessons: List<Lesson>): Completable {
    }
}
