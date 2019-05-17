package com.mmozhaiskyi.dataapi.datastore

import com.mmozhaiskyi.dataapi.RozkladService
import com.mmozhaiskyi.datastore.LessonsDataStore
import com.mmozhaiskyi.model.Lesson
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class LessonDataStoreImpl : LessonsDataStore.Remote, KoinComponent {

    private val service: RozkladService by inject()

    override fun getLessonsByTeacher(teacherId: String): Observable<List<Lesson>> {
        return service.getLessonByTeacher(teacherId)
            .map { result ->
                result.data.map { it.toModel() }
            }
    }

    override fun getLessonsByGroup(groupId: String): Observable<List<Lesson>> {
        return service.getLessonsByGroup(groupId)
            .map { result ->
                result.data.map { it.toModel() }
            }
    }
}
