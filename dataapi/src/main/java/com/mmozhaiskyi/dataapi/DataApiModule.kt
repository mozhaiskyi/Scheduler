package com.mmozhaiskyi.dataapi

import com.mmozhaiskyi.dataapi.datastore.GroupDataStoreImpl
import com.mmozhaiskyi.datastore.GroupDataStore
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DataApiModule {

    operator fun invoke() = module {

        single<GroupDataStore.Remote> { GroupDataStoreImpl() }

        single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }

        single<Converter.Factory> { GsonConverterFactory.create() }

        single<Retrofit> {
            Retrofit.Builder()
                .addCallAdapterFactory(get())
                .addConverterFactory(get())
                .baseUrl(RozkladService.BASE_URL)
                .build()
        }

        single<RozkladService> { get<Retrofit>().create(RozkladService::class.java) }
    }
}