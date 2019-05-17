package com.mmozhaiskyi.usecase

import com.mmozhaiskyi.model.Group
import com.mmozhaiskyi.model.Teacher
import com.mmozhaiskyi.repository.GroupRepository
import com.mmozhaiskyi.repository.TeacherRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

sealed class SearchResult {

    data class TeacherResult(val data: Teacher): SearchResult()

    data class GroupResult(val data: Group): SearchResult()
}

class SearchUseCase : KoinComponent {

    private val groupRepository: GroupRepository by inject()
    private val teacherRepository: TeacherRepository by inject()

    fun search(query: Observable<String>): Observable<List<SearchResult>> = query
        .filter { it.isNotBlank() }
        .debounce(300, TimeUnit.MILLISECONDS)
        .switchMap { q ->
            val zipper = BiFunction { groups: List<Group>, teachers: List<Teacher> ->
                groups.map(SearchResult::GroupResult) + teachers.map(SearchResult::TeacherResult)
            }
            Observable.zip(
                groupRepository.getGroupsByQuery(q),
                teacherRepository.getTeacherByQuery(q),
                zipper
            )
        }
}
