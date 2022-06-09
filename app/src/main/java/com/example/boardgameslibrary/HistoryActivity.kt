package com.example.boardgameslibrary

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.boardgameslibrary.database.HistoryDBHandler
import com.example.boardgameslibrary.model.Game
import com.example.boardgameslibrary.model.History
import java.text.SimpleDateFormat

class HistoryActivity : BasicListActivity() {
    private lateinit var titleGame: TextView
    private lateinit var mainHistory: LinearLayout
    private lateinit var historyList: MutableList<History>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        titleGame = findViewById(R.id.titleGame)
        mainHistory = findViewById(R.id.mainHistory)

        val title = intent.getStringExtra("title")
        val gameId = intent.getLongExtra("id", 0)

        titleGame.text = title

        val historyDBHandler = HistoryDBHandler(this, null, null, 1)
        historyList = historyDBHandler.getHistoryList(gameId)

        historyList.sortBy { it.synchronizationDate }

        for(history in historyList){
            displayHistory(history)
        }
    }

    private fun displayHistory(history: History){
        linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        linearLayout.setBackgroundColor(Color.parseColor("#7aa5ca"))

        val rankingParam = mainHistory.layoutParams as ViewGroup.MarginLayoutParams
        rankingParam.setMargins(10,10,10,10)
        rankingParam.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        linearLayout.layoutParams = rankingParam
        mainHistory.addView(linearLayout)

        history.synchronizationDate?.hours = history.synchronizationDate?.hours?.plus(2)!!
        val date = history.synchronizationDate
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        addField(formatter.format(date), 1.0f)

        addField(history.rankingPosition.toString(), 1.0f)
    }
}