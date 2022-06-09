package com.example.boardgameslibrary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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


class ConfigurationActivity: BasicSynchronizationActivity() {
    private lateinit var personName: EditText
    private lateinit var wait: TextView
    private lateinit var confirm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        personName = findViewById(R.id.personName)
        confirm = findViewById(R.id.confirm)
        wait = findViewById(R.id.wait)

        confirm.setOnClickListener(){
            wait.visibility = View.VISIBLE
//            confirm.text = "Wait for synchronization" //TODO: naprawic to
//            confirm.isEnabled = false
            synchronize(personName.text.toString())

            //TODO: zablokowac mozliwosc cofania sie strzalka
        }
    }
}