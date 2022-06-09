package com.example.boardgameslibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.boardgameslibrary.database.GameDBHandler
import com.example.boardgameslibrary.database.HistoryDBHandler
import com.example.boardgameslibrary.database.UserDBHandler
import com.example.boardgameslibrary.model.User
import com.example.boardgameslibrary.retrofit.BoardGameGeekApi
import com.example.boardgameslibrary.retrofit.RetrofitHelper
import java.text.SimpleDateFormat

class SynchronizationActivity : BasicSynchronizationActivity() { //TODO: pasek postepu i spytanie czy na pewno synchronizowac
    private lateinit var lastSynchronizationDate: TextView
    private lateinit var synchronizeDataButton: Button
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_synchronization)

        lastSynchronizationDate = findViewById(R.id.lastSynchronizationDate)
        synchronizeDataButton = findViewById(R.id.synchronizeDataButton)

        val boardGameGeekApi = RetrofitHelper.getInstance().create(BoardGameGeekApi::class.java) //TODO: poczyscic kod
        val userDBHandler = UserDBHandler(this, null, null, 1)
        val gameDBHandler = GameDBHandler(this, null, null, 1)
        val historyDBHandler = HistoryDBHandler(this, null, null, 1)
        user = userDBHandler.findUser()

        user?.synchronizationDate?.hours = user?.synchronizationDate?.hours?.plus(2)!!
        val date = user?.synchronizationDate
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        lastSynchronizationDate.text = formatter.format(date)

        synchronizeDataButton.setOnClickListener(){
            gameDBHandler.clearTable()
            userDBHandler.clearTable()

            synchronize(user!!.name.toString())
        }
    }
}