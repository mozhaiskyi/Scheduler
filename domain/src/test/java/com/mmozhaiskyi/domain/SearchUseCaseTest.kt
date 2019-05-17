package com.mmozhaiskyi.domain

import com.mmozhaiskyi.DomainModule
import com.mmozhaiskyi.datastore.GroupDataStore
import com.mmozhaiskyi.datastore.TeacherDataStore
import com.mmozhaiskyi.model.Group
import com.mmozhaiskyi.model.Teacher
import com.mmozhaiskyi.repository.GroupRepository
import com.mmozhaiskyi.repository.TeacherRepository
import com.mmozhaiskyi.usecase.SearchResult
import com.mmozhaiskyi.usecase.SearchUseCase
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class SearchUseCaseTest : KoinTest {

    private val teacherRepository = mock<TeacherRepository>()
    private val groupRepository = mock<GroupRepository>()

    private val searchUseCase: SearchUseCase by inject()

    private val testModule = module {

        single<GroupDataStore.Remote> { mock() }

        single<TeacherDataStore.Remote> { mock() }
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
    fun emptyQueryReturnNotEmit() {
        val emptyQuery = Observable.just("")
        searchUseCase.search(emptyQuery).test()
            .assertNoErrors()
            .assertNoValues()
    }

    @Test
    fun searchContainTeachersAndGroups() {

        whenever(teacherRepository.getTeacherByQuery(any())).then { Observable.just(teachers) }
        whenever(groupRepository.getGroupsByQuery(any())).then { Observable.just(groups) }

        searchUseCase.search(Observable.just("po")).test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0) {
                it.filterIsInstance(SearchResult.TeacherResult::class.java).isNotEmpty() &&
                        it.filterIsInstance(SearchResult.GroupResult::class.java).isNotEmpty()
            }
    }
}
