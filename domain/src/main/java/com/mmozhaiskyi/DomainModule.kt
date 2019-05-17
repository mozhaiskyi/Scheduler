package com.mmozhaiskyi

import com.mmozhaiskyi.repository.GroupRepository
import com.mmozhaiskyi.repository.GroupRepositoryImpl
import com.mmozhaiskyi.repository.TeacherRepository
import com.mmozhaiskyi.repository.TeacherRepositoryImpl
import com.mmozhaiskyi.usecase.SearchUseCase
import org.koin.dsl.module

object DomainModule {

    operator fun invoke() = module {

        single<GroupRepository> { GroupRepositoryImpl() }

        single<TeacherRepository> { TeacherRepositoryImpl() }

        single { SearchUseCase() }
    }
}
