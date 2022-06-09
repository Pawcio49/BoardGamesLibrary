package com.example.boardgameslibrary.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.boardgameslibrary.dto.GameDto
import com.example.boardgameslibrary.model.Game

class GameDBHandler(context: Context, name: String?,
                    factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context,
    DATABASE_NAME, factory, DATABASE_VERSION
) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "gameDB.db"
        val TABLE_GAMES = "games"
        val COLUMN_ID = "id"
        val COLUMN_GAME_ID = "game_id"
        val COLUMN_TITLE = "title"
        val COLUMN_ORIGINAL_TITLE = "original_title"
        val COLUMN_PUBLISHMENT_YEAR = "publishment_year"
        val COLUMN_CURRENT_RANKING_POSITION = "current_ranking_position"
        val COLUMN_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE " + TABLE_GAMES +"(" + COLUMN_ID +
                " INTEGER PRIMARY KEY, " + COLUMN_GAME_ID + " INTEGER, " + COLUMN_TITLE +
                " TEXT, " + COLUMN_ORIGINAL_TITLE +  " TEXT, " + COLUMN_PUBLISHMENT_YEAR +
                " INTEGER, " + COLUMN_CURRENT_RANKING_POSITION +  " INTEGER, " + COLUMN_IMAGE +
                " TEXT" + ")")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_GAMES)
        onCreate(db)
    }

    fun exist(): Boolean{
        val db = this.writableDatabase
        if(db == null || !db.isOpen()){
            return false
        }
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_GAMES", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count > 0
    }

    fun addGameAndIsAddition(game: GameDto): Boolean {
        val values = ContentValues()
        values.put(COLUMN_GAME_ID, game.objectId)
        values.put(COLUMN_TITLE, game.title)
        values.put(COLUMN_ORIGINAL_TITLE, game.originalName)
        values.put(COLUMN_PUBLISHMENT_YEAR, game.publishmentYear)
        values.put(COLUMN_IMAGE, game.thumbnail)

        var rankingValue = "0"
        var isAddition = false
        for(rank in game.stats!!.rating?.ranks!!){
            if(rank.type == "subtype" && rank.name == "boardgame"){
                if(rank.value == "Not Ranked"){
                    rankingValue = "0"
                    isAddition = true
                } else {
                    rankingValue = rank.value.toString()
                }
            }
        }

        values.put(COLUMN_CURRENT_RANKING_POSITION, rankingValue)
        val db = this.writableDatabase
        db.insert(TABLE_GAMES, null, values)
        db.close()

        return isAddition;
    }

    fun getGames(): MutableList<Game> {
        val query = "SELECT * FROM $TABLE_GAMES WHERE $COLUMN_CURRENT_RANKING_POSITION NOT LIKE 0"
        return findGames(query)
    }

    fun getAdditions(): MutableList<Game> {
        val query = "SELECT * FROM $TABLE_GAMES WHERE $COLUMN_CURRENT_RANKING_POSITION LIKE 0"
        return findGames(query)
    }

    private fun findGames(query: String): MutableList<Game> {
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        val games = mutableListOf<Game>()

        if (cursor.moveToFirst()){
            games.add(createGame(cursor))
        }

        while(!cursor.isLast){
            if (cursor.moveToNext()){
                games.add(createGame(cursor))
            }
        }

        cursor.close()
        db.close()
        return games
    }

    private fun createGame(cursor: Cursor): Game {
        val game = Game()
        game.id = cursor.getInt(0)
        game.gameId = cursor.getLong(1)
        game.title = cursor.getString(2)
        game.originalTitle = cursor.getString(3)
        game.publishmentYear = cursor.getInt(4)
        game.currentRankingPosition = cursor.getInt(5)
        game.image = cursor.getString(6)
        return game
    }

    fun clearTable() {
        val db = this.writableDatabase
        db.delete(TABLE_GAMES, null, null)
        db.close()
    }
}