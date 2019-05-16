package com.mmozhaiskyi.dataapi

import com.mmozhaiskyi.dataapi.model.GroupApiModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RozkladService {

    @GET("groups")
    fun getGroupByQuery(@Query("search") query: String): Observable<GroupsResult>

    companion object {

        internal const val BASE_URL = "https://api.rozklad.org.ua/v2/"
    }
}

internal data class GroupsResult(val data: List<GroupApiModel>)