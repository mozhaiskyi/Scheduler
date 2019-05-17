package com.mmozhaiskyi.datadb.dao

import com.mmozhaiskyi.datadb.SchedulerQueries
import com.mmozhaiskyi.model.Teacher
import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class TeacherDao : KoinComponent {

    private val queries: SchedulerQueries by inject()

    fun insertSync(teacher: Teacher) {
        with(teacher) {
            queries.insertTeacher(id, name, fullName, shortName, rating)
        }
    }

    fun deleteSync(ids: List<String>) {
        queries.deleteTeacherByIds(ids)
    }

    fun getAllByIds(ids: List<String>): Observable<List<Teacher>> {

        val q = queries.getTeachersByIds(ids, ::Teacher)

        return q.asObservable()
            .mapToList()
    }
}
