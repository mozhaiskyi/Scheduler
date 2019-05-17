package com.mmozhaiskyi.datadb.db

import com.squareup.sqldelight.ColumnAdapter

class IdsAdapter : ColumnAdapter<List<String>, String> {

    override fun decode(databaseValue: String): List<String> {
        val source = databaseValue.removePrefix(SEPARATOR).removeSuffix(SEPARATOR)
        return source.split(SEPARATOR)
    }

    override fun encode(value: List<String>): String {
        return SEPARATOR + value.joinToString(SEPARATOR) + SEPARATOR
    }

    companion object {

        private const val SEPARATOR = ":"
    }
}
