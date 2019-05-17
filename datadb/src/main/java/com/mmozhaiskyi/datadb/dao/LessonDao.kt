package com.mmozhaiskyi.datadb.dao

import androidx.annotation.VisibleForTesting
import com.mmozhaiskyi.datadb.SchedulerQueries
import com.mmozhaiskyi.model.Lesson
import io.reactivex.Completable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class LessonDao : KoinComponent {

    private val queries: SchedulerQueries by inject()

    private val groupDao: GroupDao by inject()
    private val teacherDao: TeacherDao by inject()
    private val roomDao: RoomDao by inject()

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

    fun getAllByGroupId(groupId: String) = Single.create<List<Lesson>> { e ->

        val q = queries.getLessonsByGroupId("%-$groupId-%")

        try {
            val fetched = q.executeAsList()
            val result = combineFetch(fetched)
            e.onSuccess(result)
        } catch (t: Throwable) {
            e.onError(t)
        }
    }

    fun getAllByTeacherId(teacherId: String) = Single.create<List<Lesson>> { e ->

        val q = queries.getLessonsByTeacherId("%-$teacherId-%")

        try {
            val fetched = q.executeAsList()
            val result = combineFetch(fetched)
            e.onSuccess(result)
        } catch (t: Throwable) {
            e.onError(t)
        }
    }

    @VisibleForTesting fun clear() = Completable.create { e ->
        try {
            val clearProcesses = listOf(
                queries::clearLessons,
                queries::clearGroups,
                queries::clearTeachers,
                queries::clearRooms
            )

            queries.transaction {
                clearProcesses.forEach { it.invoke() }
            }

            e.onComplete()
        } catch (t: Throwable) {
            e.onError(t)
        }
    }

    private fun combineFetch(fetched: List<com.mmozhaiskyi.datadb.Lesson>): List<Lesson> {
        return fetched.map { model ->
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
                    teacherDao.getAllByIds(model.teachersIds),
                    groupDao.getAllByIds(model.groupsIds),
                    roomDao.getAllByIds(model.roomsIds)
                )
            }
        }
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
