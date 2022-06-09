package com.example.boardgameslibrary

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.boardgameslibrary.database.GameDBHandler
import com.example.boardgameslibrary.model.Game

class GameListActivity : BasicListActivity() {
    private lateinit var mainLayoutGames: LinearLayout
    private lateinit var games: MutableList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        mainLayoutGames = findViewById(R.id.mainLayoutGames)

        val gameDBHandler = GameDBHandler(this, null, null, 1)
        games = gameDBHandler.getGames()
        for(game in games){
            displayGame(game)
        }
    }

    private fun displayGame(game: Game){
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        linearLayout.setBackgroundColor(Color.parseColor("#7aa5ca"))

        val rankingParam = mainLayoutGames.layoutParams as ViewGroup.MarginLayoutParams
        rankingParam.setMargins(10,10,10,10)
        rankingParam.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        linearLayout.layoutParams = rankingParam
        mainLayoutGames.addView(linearLayout)

        addField(linearLayout, game.id.toString(),1.2f)

        addImage(linearLayout, game.image.toString(), 1.0f)

        addField(linearLayout, game.title.toString(),1.0f)

        addField(linearLayout, game.publishmentYear.toString(), 1.15f)

        addField(linearLayout, game.currentRankingPosition.toString(), 1.05f)
    }
}