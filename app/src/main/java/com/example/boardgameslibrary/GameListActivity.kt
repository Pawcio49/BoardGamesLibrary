package com.example.boardgameslibrary

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.boardgameslibrary.database.GameDBHandler
import com.example.boardgameslibrary.model.Game

class GameListActivity : BasicListActivity() {
    private lateinit var mainLayoutGames: LinearLayout
    private lateinit var yearHeaderGame: TextView
    private lateinit var titleHeaderGame: TextView
    private lateinit var rankingHeaderGame: TextView
    private lateinit var games: MutableList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        mainLayoutGames = findViewById(R.id.mainLayoutGames)
        yearHeaderGame = findViewById(R.id.yearHeaderGame)
        titleHeaderGame = findViewById(R.id.titleHeaderGame)
        rankingHeaderGame = findViewById(R.id.rankingHeaderGame)

        val gameDBHandler = GameDBHandler(this, null, null, 1)
        games = gameDBHandler.getGames()
        for(game in games){
            displayGame(game)
        }

        yearHeaderGame.setOnClickListener(){
            mainLayoutGames.removeAllViews()
            games.sortBy { it.publishmentYear }
            for(game in games){
                displayGame(game)
            }
        }
        titleHeaderGame.setOnClickListener(){
            mainLayoutGames.removeAllViews()
            games.sortBy { it.title }
            for(game in games){
                displayGame(game)
            }
        }
        rankingHeaderGame.setOnClickListener(){
            mainLayoutGames.removeAllViews()
            games.sortBy { it.currentRankingPosition }
            for(game in games){
                displayGame(game)
            }
        }
    }

    private fun displayGame(game: Game){
        linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        linearLayout.setBackgroundColor(Color.parseColor("#7aa5ca"))

        val rankingParam = mainLayoutGames.layoutParams as ViewGroup.MarginLayoutParams
        rankingParam.setMargins(10,10,10,10)
        rankingParam.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        linearLayout.layoutParams = rankingParam
        mainLayoutGames.addView(linearLayout)

        addField(game.id.toString(),1.2f)

        addImage(game.image.toString(), 1.0f)

        addField(game.title.toString(),1.0f)

        addField(game.publishmentYear.toString(), 1.15f)

        addField(game.currentRankingPosition.toString(), 1.05f)
    }


}