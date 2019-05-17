package com.mmozhaiskyi.repository

import com.mmozhaiskyi.datastore.TeacherDataStore
import com.mmozhaiskyi.model.Teacher
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

interface TeacherRepository {

    fun getTeacherByQuery(query: String): Observable<List<Teacher>>
}

internal class TeacherRepositoryImpl : TeacherRepository, KoinComponent {

    private val remote: TeacherDataStore.Remote by inject()

    override fun getTeacherByQuery(query: String): Observable<List<Teacher>> {

        if (query.length < 3) return Observable.just(emptyList())

        return remote.getTeachersByQuery(query)
    }
}
