package com.mmozhaiskyi.datadb.dao

import com.mmozhaiskyi.datadb.SchedulerQueries
import com.mmozhaiskyi.model.Group
import com.mmozhaiskyi.model.Lesson
import com.mmozhaiskyi.model.Room
import com.mmozhaiskyi.model.Teacher
import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function3
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

    fun delete(lesson: Lesson) = Completable
        .create { e ->
            try {
                deleteSync(lesson)

                e.onComplete()
            } catch (t: Throwable) {
                e.onError(t)
            }
        }

    fun delete(lessons: List<Lesson>) = Completable
        .create { e ->
            try {
                queries.transaction {
                    lessons.forEach { lesson -> deleteSync(lesson) }
                }

                e.onComplete()
            } catch (t: Throwable) {
                e.onError(t)
            }
        }

    fun getAllByGroupId(groupId: String): Observable<List<Lesson>> {

        val q = queries.getLessonsByGroupId(":$groupId:")

        return q.asObservable()
            .mapToList()
            .flatMapSingle { models -> combineFetch(models) }
    }

    fun getAllByTeacherId(teacherId: String): Observable<List<Lesson>> {

        val q = queries.getLessonsByTeacherId(":$teacherId:")

        return q.asObservable()
            .mapToList()
            .flatMapSingle { models -> combineFetch(models) }
    }

    private fun combineFetch(fetched: List<com.mmozhaiskyi.datadb.Lesson>): Single<List<Lesson>> {
        return Observable.fromIterable(fetched)
            .flatMap { model ->
                val zipper = Function3 { groups: List<Group>, teachers: List<Teacher>, rooms: List<Room> ->
                    with(model) {
                        Lesson(
                            id,
                            dayNumber,
                            day,
                            name,
                            fullName,
                            lessonNumber,
                            type,
                            weekNumber,
                            timeStart,
                            timeEnd,
                            rate.toInt(),
                            teachers,
                            groups,
                            rooms
                        )
                    }
                }
                Observable.zip(
                    groupDao.getAllByIds(model.groupsIds),
                    teacherDao.getAllByIds(model.teachersIds),
                    roomDao.getAllByIds(model.roomsIds),
                    zipper
                )
            }
            .toList()
    }

    private fun deleteSync(lesson: Lesson) {
        queries.deleteLesson(lesson.id)

        groupDao.deleteSync(lesson.groups.map { it.id })
        teacherDao.deleteSync(lesson.teachers.map { it.id })
        roomDao.deleteSync(lesson.rooms.map { it.id })
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
                type,
                weekNumber,
                timeStart,
                timeEnd,
                rate.toLong(),
                teachers.map { it.id },
                groups.map { it.id },
                rooms.map { it.id }
            )

            teachers.forEach { teacher -> teacherDao.insertSync(teacher) }
            groups.forEach { group -> groupDao.insertSync(group) }
            rooms.forEach { room -> roomDao.insertSync(room) }
        }
    }
}
