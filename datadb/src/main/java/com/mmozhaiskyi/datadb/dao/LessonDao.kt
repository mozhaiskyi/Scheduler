package com.mmozhaiskyi.datadb.dao

import com.mmozhaiskyi.datadb.SchedulerQueries
import com.mmozhaiskyi.model.Lesson
import io.reactivex.Completable
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class LessonDao : KoinComponent {

    private val queries: SchedulerQueries by inject()

    private val groupDao: GroupDao by inject()
    private val teacherDao: TeacherDao by inject()
    private val roomDao: RoomDao by inject()

    fun insert(lesson: Lesson) = Completable
        .create { e ->
            try {
                insertSync(lesson)

                e.onComplete()
            } catch (t: Throwable) {
                e.onError(t)
            }

        }

    fun insert(lessons: List<Lesson>) = Completable
        .create { e ->
            try {
                queries.transaction {
                    lessons.forEach { lesson -> insertSync(lesson) }
                }

                e.onComplete()
            } catch (t: Throwable) {
                e.onError(t)
            }
        }

    fun delete(id: String) = Completable
        .create { e ->
            try {
                queries.deleteLesson(id)

                e.onComplete()
            } catch (t: Throwable) {
                e.onError(t)
            }
        }

    fun delete(ids: List<String>) = Completable
        .create { e ->
            try {
                queries.transaction {
                    ids.forEach { id -> queries.deleteLesson(id) }
                }

                e.onComplete()
            } catch (t: Throwable) {
                e.onError(t)
            }
        }

    fun getAllByGroupId(groupId: String): Observable<List<Lesson>> {
        val q = queries.
    }

    fun getAllByTeacherId(teacherId: String): Observable<List<Lesson>> {

    }

    private fun insertSync(lesson: Lesson) {
        with(lesson) {
            queries.insertLesson(
                id,
                dayNumber,
                day,
                name,
                fullName,
                lessonNumber,
                timeStart,
                timeEnd,
                rate.toLong(),
                teachers.map { it.id },
                groups.map { it.id },
                rooms.map { it.id }
            )
        }
    }
}