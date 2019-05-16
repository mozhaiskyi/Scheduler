package com.mmozhaiskyi.datastore

import com.mmozhaiskyi.model.Teacher
import io.reactivex.Observable

interface TeacherDataStore {

    interface Remote {

        fun getTeachersByQuery(query: String): Observable<List<Teacher>>
    }
}
