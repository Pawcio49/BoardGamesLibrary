package com.example.boardgameslibrary.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.boardgameslibrary.model.User
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UserDBHandler(context: Context, name: String?,
                    factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context,
    DATABASE_NAME, factory, DATABASE_VERSION
) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "userDB.db"
        val TABLE_USERS = "users"
        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_GAME_AMOUNT = "game_amount"
        val COLUMN_GAME_ADDITION_AMOUNT = "game_addition_amount"
        val COLUMN_SYNCHRONIZATION_DATE = "synchronization_date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " + TABLE_USERS +"(" + COLUMN_ID +
                " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, "  + COLUMN_GAME_AMOUNT +
                " INTEGER, " + COLUMN_GAME_ADDITION_AMOUNT +  " INTEGER, " +
                COLUMN_SYNCHRONIZATION_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues()
        values.put(COLUMN_NAME, user.name)
        values.put(COLUMN_GAME_AMOUNT, user.gameAmount)
        values.put(COLUMN_GAME_ADDITION_AMOUNT, user.gameAdditionAmount)

        val db = this.writableDatabase
        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    fun findUser() : User? {
        val query = "SELECT * FROM $TABLE_USERS"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var user: User? = null

        if (cursor.moveToFirst()){
            user = User()
            user.name = cursor.getString(1)
            user.gameAmount = cursor.getInt(2)
            user.gameAdditionAmount = cursor.getInt(3)

            val synchronizationDate = cursor.getString(4)
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            user.synchronizationDate = formatter.parse(synchronizationDate)

            cursor.close()
        }
        db.close()
        return user
    }

    fun clearTable() {
        val db = this.writableDatabase
        db.delete(TABLE_USERS, null, null)
        db.close()
    }
}