package com.example.boardgameslibrary.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.boardgameslibrary.dto.GameDto

class HistoryDBHandler(
    context: Context, name: String?,
    factory: SQLiteDatabase.CursorFactory?, version: Int
) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, factory, DATABASE_VERSION
) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "historyDB.db"
        val TABLE_HISTORY = "history"
        val COLUMN_ID = "id"
        val COLUMN_GAME_ID = "game_id"
        val COLUMN_RANKING_POSITION = "ranking_position"
        val COLUMN_SYNCHRONIZATION_DATE = "synchronization_date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE " + TABLE_HISTORY + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY, " + COLUMN_GAME_ID + " INTEGER, " + COLUMN_RANKING_POSITION +
                " INTEGER, " + COLUMN_SYNCHRONIZATION_DATE +
                " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_HISTORY)
        onCreate(db)
    }

    fun addHistory(game: GameDto){
        val values = ContentValues()
        values.put(COLUMN_GAME_ID, game.objectId)

        var rankingValue = "0"
        for(rank in game.stats!!.rating?.ranks!!){
            if(rank.type == "subtype" && rank.name == "boardgame" && rank.value != "Not Ranked"){
                rankingValue = rank.value.toString()
            }
        }
        values.put(COLUMN_RANKING_POSITION, rankingValue)

        val db = this.writableDatabase
        db.insert(TABLE_HISTORY, null, values)
        db.close()
    }
}