package com.mmozhaiskyi.dataapi

import com.mmozhaiskyi.datastore.TeacherDataStore
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.HttpException

class TeacherDataStoreTest : KoinTest {

    private val teacherDataStore: TeacherDataStore.Remote by inject()

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
    fun throwErrorIfQueryLengthIsLessThanThree() {
        teacherDataStore.getTeachersByQuery("Вл")
            .test()
            .assertError { it is HttpException }
    }

    @Test
    fun searchProcessWithoutError() {
        teacherDataStore.getTeachersByQuery("Влад")
            .test()
            .assertNoErrors()
            .assertValueCount(1)
    }
}
