package com.example.boardgameslibrary

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import com.example.boardgameslibrary.database.GameDBHandler
import com.example.boardgameslibrary.model.Game

class AdditionActivity : BasicListActivity() {
    private lateinit var mainLayout: LinearLayout
    private lateinit var titleHeaderAddition: TextView
    private lateinit var yearHeaderAddition: TextView
    private lateinit var games: MutableList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addition)

        mainLayout = findViewById(R.id.mainLayout)
        yearHeaderAddition = findViewById(R.id.yearHeaderAddition)
        titleHeaderAddition = findViewById(R.id.titleHeaderAddition)

        val gameDBHandler = GameDBHandler(this, null, null, 1)
        games = gameDBHandler.getAdditions()
        for(game in games){
            displayGame(game)
        }

        yearHeaderAddition.setOnClickListener(){
            mainLayout.removeAllViews()
            games.sortBy { it.publishmentYear }
            for(game in games){
                displayGame(game)
            }
        }
        titleHeaderAddition.setOnClickListener(){
            mainLayout.removeAllViews()
            games.sortBy { it.title }
            for(game in games){
                displayGame(game)
            }
        }
    }

    private fun displayGame(game: Game){
        linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        linearLayout.setBackgroundColor(Color.parseColor("#7aa5ca"))

        val rankingParam = mainLayout.layoutParams as ViewGroup.MarginLayoutParams
        rankingParam.setMargins(10,10,10,10)
        rankingParam.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        linearLayout.layoutParams = rankingParam
        mainLayout.addView(linearLayout)

        addField(game.id.toString(),1.2f)

        addImage(game.image.toString(), 1.0f)

        addField(game.title.toString(),1.0f)

        addField(game.publishmentYear.toString(), 1.15f)
    }
}