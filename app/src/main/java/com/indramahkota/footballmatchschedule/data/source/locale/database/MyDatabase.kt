package com.indramahkota.footballmatchschedule.data.source.locale.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.indramahkota.footballmatchschedule.data.source.locale.entity.MatchEntity
import org.jetbrains.anko.db.*

class MyDatabase(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: MyDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabase {
            if (instance == null) {
                instance = MyDatabase(ctx.applicationContext)
            }
            return instance as MyDatabase
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            MatchEntity.TABLE_NAME, true,
            MatchEntity.Column.ID_EVENT to TEXT + PRIMARY_KEY,
            MatchEntity.Column.ID_HOME_TEAM to TEXT,
            MatchEntity.Column.ID_AWAY_TEAM to TEXT,
            MatchEntity.Column.DATE_EVENT to TEXT,
            MatchEntity.Column.HOME_TEAM to TEXT,
            MatchEntity.Column.AWAY_TEAM to TEXT,
            MatchEntity.Column.HOME_SCORE to TEXT,
            MatchEntity.Column.AWAY_SCORE to TEXT,
            MatchEntity.Column.HOME_IMAGE to TEXT,
            MatchEntity.Column.AWAY_IMAGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MatchEntity.TABLE_NAME, true)
    }
}