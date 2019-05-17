package com.mmozhaiskyi.domain

import com.mmozhaiskyi.DomainModule
import com.mmozhaiskyi.datastore.LessonDataStore
import com.mmozhaiskyi.model.Lesson
import com.mmozhaiskyi.repository.LessonRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class LessonRepositoryTest : KoinTest {

    private val remote = mock<LessonDataStore.Remote>()
    private val cache = mock<LessonDataStore.Cache>()

    private val repository: LessonRepository by inject()

    private val testModule = module {

        single { remote }

        single { cache }
    }

    @Before
    fun init() {
        startKoin {
            modules(
                DomainModule(),
                testModule
            )
        }
    }

    @After
    fun clear() {
        stopKoin()
    }

    @Test
    fun getLessonsByGroupWhenCacheIsEmpty() {

        val groupId = "4312"

        whenever(cache.getLessonsByGroup(groupId)).then { Observable.just(emptyList<Lesson>()) }
        whenever(cache.saveLessons(lessons)).then { Completable.complete() }
        whenever(cache.deleteLessonsByGroup(groupId)).then { Completable.complete() }
        whenever(remote.getLessonsByGroup(groupId)).then { Observable.just(lessons) }


        repository.getLessonsByGroup(groupId).test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0, lessons)
    }

    @Test
    fun getLessonsByGroupFromCache() {

        val groupId = "4312"

        whenever(cache.getLessonsByGroup(groupId)).then { Observable.just(lessons) }


        repository.getLessonsByGroup(groupId).test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0, lessons)
    }

    @Test
    fun updateLessonsByGroup() {

        val groupId = "4312"

        whenever(cache.getLessonsByGroup(groupId)).then { Observable.just(emptyList<Lesson>()) }
        whenever(cache.saveLessons(lessons)).then { Completable.complete() }
        whenever(cache.deleteLessonsByGroup(groupId)).then { Completable.complete() }
        whenever(remote.getLessonsByGroup(groupId)).then { Observable.just(lessons) }


        repository.getLessonsByGroup(groupId).test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0, lessons)
    }
}
