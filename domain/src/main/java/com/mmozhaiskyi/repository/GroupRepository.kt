package com.mmozhaiskyi.repository

import com.mmozhaiskyi.datastore.GroupDataStore
import com.mmozhaiskyi.model.Group
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

interface GroupRepository {

    fun getGroupsByQuery(query: String): Observable<List<Group>>
}

internal class GroupRepositoryImpl : GroupRepository, KoinComponent {

    private val remote: GroupDataStore.Remote by inject()

    override fun getGroupsByQuery(query: String): Observable<List<Group>> {
        return remote.getGroupsByQuery(query)
    }
}
