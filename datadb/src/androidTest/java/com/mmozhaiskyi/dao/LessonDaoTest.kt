package com.mmozhaiskyi.dao

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.mmozhaiskyi.datadb.DataDbModule
import com.mmozhaiskyi.datadb.dao.LessonDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class LessonDaoTest : KoinTest {

    private val lessonDao: LessonDao by inject()

    @Before
    fun init() {
        val context = InstrumentationRegistry.getInstrumentation().context
        startKoin {
            modules(DataDbModule())
            androidContext(context)
        }
    }

    @After
    fun clear() {
        stopKoin()
        lessonDao.clear().blockingAwait()
    }

    @Test
    fun insertWorkCorrect() {

        lessonDao.insert(lessons)
            .test()
            .assertNoErrors()
            .assertComplete()

        lessonDao.getAllByGroupId("1")
            .test()
            .assertNoErrors()
            .assertValue(lessons)
    }
}
