package com.example.boardgameslibrary

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.boardgameslibrary.database.GameDBHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gameDbHandler = GameDBHandler(this, null, null, 1)
        val intent = Intent(this, ConfigurationActivity::class.java)

        GlobalScope.launch {
            if (!gameDbHandler.exist()) {
                startForResult.launch(intent)
            }
        }
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
//            if (intent != null) {
//
//            }
        }
    }


}