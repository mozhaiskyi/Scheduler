package com.mmozhaiskyi.datadb.db

import com.squareup.sqldelight.ColumnAdapter

class IdsAdapter : ColumnAdapter<List<String>, String> {

    override fun decode(databaseValue: String): List<String> {
        return databaseValue.split(SEPARATOR)
    }

    override fun encode(value: List<String>): String {
        return value.joinToString(SEPARATOR)
    }

    companion object {

        private const val SEPARATOR = ":"
    }
}
