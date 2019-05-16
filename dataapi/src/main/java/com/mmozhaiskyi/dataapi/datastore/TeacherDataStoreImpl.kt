package com.mmozhaiskyi.dataapi.datastore

import com.mmozhaiskyi.dataapi.RozkladService
import com.mmozhaiskyi.dataapi.util.QueryFormatter
import com.mmozhaiskyi.datastore.TeacherDataStore
import com.mmozhaiskyi.model.Teacher
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class TeacherDataStoreImpl : TeacherDataStore.Remote, KoinComponent {

    private val service: RozkladService by inject()

    override fun getTeachersByQuery(query: String): Observable<List<Teacher>> {
        val q = QueryFormatter.format(query)
        return service.getTeachersByQuery(q)
            .map { result ->
                result.data.map { apiModel -> apiModel.toModel() }
            }
    }
}
