package com.mmozhaiskyi.dataapi.datastore

import com.mmozhaiskyi.dataapi.RozkladService
import com.mmozhaiskyi.dataapi.util.QueryFormatter
import com.mmozhaiskyi.datastore.GroupDataStore
import com.mmozhaiskyi.model.Group
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class GroupDataStoreImpl : GroupDataStore.Remote, KoinComponent {

    private val service: RozkladService by inject()

    override fun getGroupsByQuery(query: String): Single<List<Group>> {
        val q = QueryFormatter.format(query)
        return service.getGroupsByQuery(q)
            .map { result ->
                result.data.map { apiModel -> apiModel.toModel() }
            }
    }
}
