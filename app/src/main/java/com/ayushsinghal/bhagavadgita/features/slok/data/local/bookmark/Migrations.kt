package com.ayushsinghal.bhagavadgita.features.slok.data.local.bookmark

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_0_1 = object : Migration(0, 1) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `bookmarks` (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")
        }
    }
}