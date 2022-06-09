package com.example.boardgameslibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.boardgameslibrary.database.GameDBHandler
import com.example.boardgameslibrary.model.Game

class AdditionActivity : AppCompatActivity() {
    private lateinit var games: MutableList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addition)

        val gameDBHandler = GameDBHandler(this, null, null, 1)
        games = gameDBHandler.getAdditions()
        for(game in games){
            println(game.id)
        }
    }
}