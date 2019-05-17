package com.mmozhaiskyi.datadb.dao

import com.mmozhaiskyi.datadb.SchedulerQueries
import com.mmozhaiskyi.model.Group
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class GroupDao : KoinComponent {

    private val queries: SchedulerQueries by inject()

    fun insertSync(teacher: Group) {
        with(teacher) {
            queries.insertGroup(id, name, okr, type)
        }
    }

    fun deleteSync(ids: List<String>) {
        queries.deleteGroupsByIds(ids)
    }

    fun getAllByIds(ids: List<String>): List<Group> {

        val q = queries.getGroupsByIds(ids, ::Group)

        return q.executeAsList()
    }
}
