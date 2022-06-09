package com.example.boardgameslibrary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.boardgameslibrary.database.GameDBHandler
import com.example.boardgameslibrary.database.UserDBHandler
import com.example.boardgameslibrary.model.User
import com.example.boardgameslibrary.retrofit.BoardGameGeekApi
import com.example.boardgameslibrary.retrofit.RetrofitHelper
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ConfigurationActivity : AppCompatActivity() {
    private lateinit var personName: EditText
    private lateinit var confirm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        personName = findViewById(R.id.personName)
        confirm = findViewById(R.id.confirm)

        val gameDBHandler = GameDBHandler(this, null, null, 1)
        val boardGameGeekApi = RetrofitHelper.getInstance().create(BoardGameGeekApi::class.java)
        confirm.setOnClickListener(){
//            confirm.text = "Wait for synchronization" //TODO: naprawic to
//            confirm.isEnabled = false

            var gameAmount = 0
            var gameAdditionAmount = 0

            runBlocking {
                val result = boardGameGeekApi.getGames(personName.text.toString()) //TODO: zeby nie bylo bledu, jak jest zly username
                for(game in result.item!!){
                    if(gameDBHandler.addGameAndIsAddition(game)){
                        gameAdditionAmount++
                    } else {
                        gameAmount++
                    }
                }
            }

            val userDBHandler = UserDBHandler(this, null, null, 1)
            
            val user = User()
            user.name = personName.text.toString()
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
    }

    private fun getCurrentDate(): String? {
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return currentDate.format(formatter)
    }

    
}