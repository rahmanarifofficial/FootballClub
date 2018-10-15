package com.rahmanarif.footballclub.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavoriteDbHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: FavoriteDbHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FavoriteDbHelper {
            if (instance == null) {
                instance = FavoriteDbHelper(ctx.applicationContext)
            }
            return instance as FavoriteDbHelper
        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(EventDB.TABLE_EVENT, true,
                EventDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EventDB.EVENT_ID to TEXT + UNIQUE,
                EventDB.HOMETEAM_ID to TEXT,
                EventDB.HOMETEAM to TEXT,
                EventDB.AWAYTEAM_ID to TEXT,
                EventDB.AWAYTEAM to TEXT,
                EventDB.HOMESCORE to TEXT,
                EventDB.AWAYSCORE to TEXT,
                EventDB.DATEEVENT to TEXT
        )
        db?.createTable(TeamDB.TABLE_TEAMS, true,
                TeamDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeamDB.TEAMS_ID to TEXT + UNIQUE,
                TeamDB.TEAMS_NAME to TEXT,
                TeamDB.BADGE_TEAM to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(EventDB.TABLE_EVENT, true)
        db?.dropTable(TeamDB.TEAMS_NAME, true)
    }
}

val Context.database: FavoriteDbHelper
    get() = FavoriteDbHelper.getInstance(applicationContext)