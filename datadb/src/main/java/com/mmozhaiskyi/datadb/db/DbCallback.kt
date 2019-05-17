package com.mmozhaiskyi.datadb.db

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.mmozhaiskyi.datadb.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver

class DbCallback : SupportSQLiteOpenHelper.Callback(DB_VERSION) {

    override fun onCreate(db: SupportSQLiteDatabase) {
        val driver = AndroidSqliteDriver(db)
        Database.Schema.create(driver)
    }

    override fun onUpgrade(db: SupportSQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {

        val DB_NAME = "schedule"
        val DB_VERSION = 1
    }
}