package com.example.boardgameslibrary.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.boardgameslibrary.dto.GameDto
import com.example.boardgameslibrary.model.History
import java.text.SimpleDateFormat

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

    fun getHistoryList(id: Long): MutableList<History> {
        val query = "SELECT * FROM $TABLE_HISTORY WHERE $COLUMN_GAME_ID = $id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        val historyList = mutableListOf<History>()

        if (cursor.moveToFirst()){
            historyList.add(createHistory(cursor))
        }

        while(!cursor.isLast){
            if (cursor.moveToNext()){
                historyList.add(createHistory(cursor))
            }
        }

        cursor.close()
        db.close()
        return historyList
    }

    private fun createHistory(cursor: Cursor): History {
        val history = History()
        history.id = cursor.getInt(0)
        history.gameId = cursor.getLong(1)
        history.rankingPosition = cursor.getInt(2)

        val synchronizationDate = cursor.getString(3)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        history.synchronizationDate = formatter.parse(synchronizationDate)
        return history
    }
}