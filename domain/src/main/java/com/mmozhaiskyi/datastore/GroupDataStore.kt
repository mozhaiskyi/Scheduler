package com.mmozhaiskyi.datastore

import com.mmozhaiskyi.model.Group
import io.reactivex.Observable

interface GroupDataStore {

    interface Remote {

        fun getGroupsByQuery(query: String): Observable<List<Group>>
    }
}