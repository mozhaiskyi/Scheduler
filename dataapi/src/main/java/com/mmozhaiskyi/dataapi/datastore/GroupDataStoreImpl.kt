package com.mmozhaiskyi.dataapi.datastore

import com.mmozhaiskyi.dataapi.RozkladService
import com.mmozhaiskyi.datastore.GroupDataStore
import com.mmozhaiskyi.model.Group
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class GroupDataStoreImpl : GroupDataStore.Remote, KoinComponent {

    private val service: RozkladService by inject()

    override fun getGroupsByQuery(query: String): Observable<List<Group>> {
        val q = "{'query':'$query'}"
        return service.getGroupByQuery(q)
            .map { result ->
                result.data.map { apiModel -> apiModel.toModel() }
            }
    }
}
