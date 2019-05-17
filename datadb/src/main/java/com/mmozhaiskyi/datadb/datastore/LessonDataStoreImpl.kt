package com.mmozhaiskyi.datadb.datastore

import com.mmozhaiskyi.datadb.dao.LessonDao
import com.mmozhaiskyi.datastore.LessonDataStore
import com.mmozhaiskyi.model.Lesson
import io.reactivex.Completable
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class LessonDataStoreImpl : LessonDataStore.Cache, KoinComponent {

    private val lessonsDao: LessonDao by inject()

    override fun getLessonsByTeacher(teacherId: String): Observable<List<Lesson>> {
        return lessonsDao.getAllByTeacherId(teacherId)
    }

    override fun getLessonsByGroup(groupId: String): Observable<List<Lesson>> {
        return lessonsDao.getAllByGroupId(groupId)
    }

    override fun deleteLessonsByTeacher(teacherId: String): Completable {
        return lessonsDao.getAllByTeacherId(teacherId)
            .firstOrError()
            .flatMapCompletable { lessons -> lessonsDao.delete(lessons) }
    }

    override fun deleteLessonsByGroup(groupId: String): Completable {
        return lessonsDao.getAllByGroupId(groupId)
            .firstOrError()
            .flatMapCompletable { lessons -> lessonsDao.delete(lessons) }
    }

    override fun saveLessons(lessons: List<Lesson>): Completable {
        return lessonsDao.insert(lessons)
    }
}
