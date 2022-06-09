package com.example.boardgameslibrary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.boardgameslibrary.database.GameDBHandler
import com.example.boardgameslibrary.database.HistoryDBHandler
import com.example.boardgameslibrary.database.UserDBHandler
import com.example.boardgameslibrary.model.User
import com.example.boardgameslibrary.retrofit.BoardGameGeekApi
import com.example.boardgameslibrary.retrofit.RetrofitHelper
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class BasicSynchronizationActivity: AppCompatActivity() {
    fun synchronize(username: String){
        val gameDBHandler = GameDBHandler(this, null, null, 1)
        val historyDBHandler = HistoryDBHandler(this, null, null, 1)
        val boardGameGeekApi = RetrofitHelper.getInstance().create(BoardGameGeekApi::class.java)

        var gameAmount = 0
        var gameAdditionAmount = 0

        runBlocking {
            val result = boardGameGeekApi.getGames(username) //TODO: zeby nie bylo bledu, jak jest zly username
            for(game in result.item!!){
                if(gameDBHandler.addGameAndIsAddition(game)){
                    gameAdditionAmount++
                } else {
                    gameAmount++
                    historyDBHandler.addHistory(game)
                }
            }
        }

        val userDBHandler = UserDBHandler(this, null, null, 1)

        val user = User()
        user.name = username
        user.gameAmount = gameAmount
        user.gameAdditionAmount = gameAdditionAmount

        userDBHandler.addUser(user)

        val data = Intent()
        data.putExtra("username", user.name)
        data.putExtra("gameAmount", user.gameAmount)
        data.putExtra("gameAdditionAmount", user.gameAdditionAmount)
        data.putExtra("synchronizationDate", getCurrentDate())
        setResult(Activity.RESULT_OK, data)

        finish()
    }

    private fun getCurrentDate(): String? {
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return currentDate.format(formatter)
    }
}