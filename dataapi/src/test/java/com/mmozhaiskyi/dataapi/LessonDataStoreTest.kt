package com.mmozhaiskyi.dataapi

import com.mmozhaiskyi.datastore.LessonsDataStore
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class LessonDataStoreTest : KoinTest {

    private val lessonDataStore: LessonsDataStore.Remote by inject()

    private val testGroupId = "4666"
    private val testTeacherId = "3232"

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
    fun fetchProcessWithoutError() {
        lessonDataStore.getLessonsByGroup(testGroupId)
            .test()
            .assertValueCount(1)
            .assertValueAt(0) { it.isNotEmpty() }
            .assertNoErrors()

        lessonDataStore.getLessonsByTeacher(testTeacherId)
            .test()
            .assertValueCount(1)
            .assertValueAt(0) { it.isNotEmpty() }
            .assertNoErrors()
    }
}
