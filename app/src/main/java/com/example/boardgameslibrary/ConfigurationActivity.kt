package com.example.boardgameslibrary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.boardgameslibrary.database.GameDBHandler
import com.example.boardgameslibrary.retrofit.BoardGameGeekApi
import com.example.boardgameslibrary.retrofit.RetrofitHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ConfigurationActivity : AppCompatActivity() {
    private lateinit var personName: EditText
    private lateinit var confirm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        val dbHandler = GameDBHandler(this, null, null, 1)

        personName = findViewById(R.id.personName)
        confirm = findViewById(R.id.confirm)
        val quotesApi = RetrofitHelper.getInstance().create(BoardGameGeekApi::class.java)

        confirm.setOnClickListener(){
            GlobalScope.launch {
                val result = quotesApi.getGames(personName.text.toString()) //TODO: zeby nie bylo bledu, jak jest zly username
                for(game in result.item!!){
                    dbHandler.addGame(game)
                }
            }
        }
    }

    
}