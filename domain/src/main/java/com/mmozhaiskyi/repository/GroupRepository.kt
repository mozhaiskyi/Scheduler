package com.mmozhaiskyi.repository

import com.mmozhaiskyi.datastore.GroupDataStore
import com.mmozhaiskyi.model.Group
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

interface GroupRepository {

    fun getGroupsByQuery(query: String): Single<List<Group>>
}

internal class GroupRepositoryImpl : GroupRepository, KoinComponent {

    private val remote: GroupDataStore.Remote by inject()

    override fun getGroupsByQuery(query: String): Single<List<Group>> {
        return remote.getGroupsByQuery(query)
    }
}
