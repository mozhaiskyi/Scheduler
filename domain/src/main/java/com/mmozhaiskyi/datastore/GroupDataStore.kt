package com.mmozhaiskyi.datastore

import com.mmozhaiskyi.model.Group
import io.reactivex.Observable
import io.reactivex.Single

interface GroupDataStore {

    interface Remote {

        fun getGroupsByQuery(query: String): Single<List<Group>>
    }
}