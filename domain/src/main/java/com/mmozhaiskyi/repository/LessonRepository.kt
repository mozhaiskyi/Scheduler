package com.mmozhaiskyi.repository

import com.mmozhaiskyi.datastore.LessonDataStore
import com.mmozhaiskyi.model.Lesson
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

interface LessonRepository {

    fun getLessonsByGroup(groupId: String): Single<List<Lesson>>

    fun updateLessonsByGroup(groupId: String): Single<List<Lesson>>

    fun getLessonsByTeacher(teacherId: String): Single<List<Lesson>>

    fun updateLessonsByTeacher(teacherId: String): Single<List<Lesson>>
}

internal class LessonRepositoryImpl : LessonRepository, KoinComponent {

    private val remote: LessonDataStore.Remote by inject()
    private val cache: LessonDataStore.Cache by inject()

    override fun getLessonsByGroup(groupId: String): Single<List<Lesson>> {
        return cache.getLessonsByGroup(groupId)
            .flatMap { cached ->
                if (cached.isEmpty()) updateLessonsByGroup(groupId)
                else Single.just(cached)
            }
    }

    override fun updateLessonsByGroup(groupId: String): Single<List<Lesson>> {
        return remote.getLessonsByGroup(groupId)
            .flatMap { fetched ->
                val clearCache = cache.deleteLessonsByGroup(groupId)
                val saveToCache = cache.saveLessons(fetched)

                return@flatMap clearCache
                    .andThen(saveToCache)
                    .andThen(Single.just(fetched))
            }
    }

    override fun getLessonsByTeacher(teacherId: String): Single<List<Lesson>> {
        return cache.getLessonsByTeacher(teacherId)
            .flatMap { cached ->
                if (cached.isEmpty()) updateLessonsByTeacher(teacherId)
                else Single.just(cached)
            }
    }

    override fun updateLessonsByTeacher(teacherId: String): Single<List<Lesson>> {
        return remote.getLessonsByTeacher(teacherId)
            .flatMap { fetched ->
                val clearCache = cache.deleteLessonsByTeacher(teacherId)
                val saveToCache = cache.saveLessons(fetched)

                return@flatMap clearCache
                    .andThen(saveToCache)
                    .andThen(Single.just(fetched))
            }
    }
}
