package com.example.boardgameslibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class ConfigurationActivity : AppCompatActivity() {
    private val USER_COLLECTION_URL = "https://boardgamegeek.com/xmlapi2/collection?username="
    private lateinit var personName: EditText
    private var userUrl: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        val dbHandler = GameDBHandler(this, null, null, 1)
        personName = findViewById(R.id.personName)

        userUrl = USER_COLLECTION_URL + personName.text


//        dbHandler.addGame()
    }

    
}