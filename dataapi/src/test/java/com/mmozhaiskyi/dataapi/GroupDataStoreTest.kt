package com.mmozhaiskyi.dataapi

import com.mmozhaiskyi.datastore.GroupDataStore
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class GroupDataStoreTest : KoinTest {

    private val groupsDataStore: GroupDataStore.Remote by inject()

    @Before
    fun init() {
        startKoin {
            modules(DataApiModule())
        }
    }

    @After
    fun clear() {
        stopKoin()
    }

    @Test
    fun searchProcessWithoutError() {
        groupsDataStore.getGroupsByQuery("ti")
            .test()
            .assertNoErrors()
            .assertValueCount(1)
    }
}
