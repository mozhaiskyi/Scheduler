package com.mmozhaiskyi.datastore

import com.mmozhaiskyi.model.Teacher
import io.reactivex.Single

interface TeacherDataStore {

    interface Remote {

        fun getTeachersByQuery(query: String): Single<List<Teacher>>
    }
}
