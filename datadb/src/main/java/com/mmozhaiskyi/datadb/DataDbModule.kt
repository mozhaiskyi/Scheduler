package com.mmozhaiskyi.datadb

import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import com.mmozhaiskyi.datadb.dao.GroupDao
import com.mmozhaiskyi.datadb.dao.LessonDao
import com.mmozhaiskyi.datadb.dao.RoomDao
import com.mmozhaiskyi.datadb.dao.TeacherDao
import com.mmozhaiskyi.datadb.db.DbCallback
import com.mmozhaiskyi.datadb.db.IdsAdapter
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.dsl.module

object DataDbModule {

    operator fun invoke() = module {

        single<SupportSQLiteOpenHelper> {
            val config = SupportSQLiteOpenHelper.Configuration.builder(get())
                .name(DbCallback.DB_NAME)
                .callback(DbCallback())
                .build()

            val factory = FrameworkSQLiteOpenHelperFactory()

            return@single factory.create(config)
        }

        single<ColumnAdapter<List<String>, String>> { IdsAdapter() }

        single {
            val driver = AndroidSqliteDriver(get<SupportSQLiteOpenHelper>())
            val adapter = Lesson.Adapter(get(), get(), get())

            return@single Database.invoke(driver, adapter)
        }

        single { get<Database>().schedulerQueries }

        single { GroupDao() }

        single { TeacherDao() }

        single { RoomDao() }

        single { LessonDao() }
    }
}
