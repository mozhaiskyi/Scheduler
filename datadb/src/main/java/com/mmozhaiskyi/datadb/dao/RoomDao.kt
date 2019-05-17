package com.mmozhaiskyi.datadb.dao

import com.mmozhaiskyi.datadb.SchedulerQueries
import com.mmozhaiskyi.model.Room
import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class RoomDao : KoinComponent {

    private val queries: SchedulerQueries by inject()

    fun insertSync(teacher: Room) {
        with(teacher) {
            queries.insertRoom(id, name, latitude, longitude)
        }
    }

    fun deleteSync(ids: List<String>) {
        queries.deleteRoomByIds(ids)
    }

    fun getAllByIds(ids: List<String>): Observable<List<Room>> {

        val q = queries.getRoomsByIds(ids, ::Room)

        return q.asObservable()
            .mapToList()
    }
}
