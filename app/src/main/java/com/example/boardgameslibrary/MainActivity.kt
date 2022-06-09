package com.example.boardgameslibrary

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.boardgameslibrary.database.GameDBHandler
import com.example.boardgameslibrary.database.UserDBHandler
import com.example.boardgameslibrary.model.User
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var username: TextView
    private lateinit var gameAmount: TextView
    private lateinit var gameAdditionAmount: TextView
    private lateinit var synchronizationDate: TextView
    private lateinit var clearDataButton: Button
    private lateinit var listOfAdditionsButton: Button

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTextViews()
        manageConfiguration()

        setListOfAdditionsButton()

        setClearDataButton()
    }

    private val startConfigurationForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                user = User()
                user!!.name = intent.getStringExtra("username")
                user!!.gameAmount = intent.getIntExtra("gameAmount", 0)
                user!!.gameAdditionAmount = intent.getIntExtra("gameAdditionAmount", 0)
                val synchronizationDate = intent.getStringExtra("synchronizationDate")
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                user!!.synchronizationDate = formatter.parse(synchronizationDate)
                setUserDetailsView()
            }
        }
    }

    private fun setTextViews(){
        username = findViewById(R.id.username)
        gameAmount = findViewById(R.id.gameAmount)
        gameAdditionAmount = findViewById(R.id.gameAdditionAmount)
        synchronizationDate = findViewById(R.id.synchronizationDate)
    }

    private fun manageConfiguration(){
        val gameDbHandler = GameDBHandler(this, null, null, 1)
        val intent = Intent(this, ConfigurationActivity::class.java)

        if (!gameDbHandler.exist()) {
            startConfigurationForResult.launch(intent)
        } else {
            val userDBHandler = UserDBHandler(this, null, null, 1)
            user = userDBHandler.findUser()
            setUserDetailsView()
        }
    }

    private fun setUserDetailsView(){
        username.text = user?.name ?: ""
        gameAmount.text = user?.gameAmount.toString()
        gameAdditionAmount.text = user?.gameAdditionAmount.toString()

        user?.synchronizationDate?.hours = user?.synchronizationDate?.hours?.plus(2)!!
        val date = user?.synchronizationDate
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        synchronizationDate.text = formatter.format(date)
    }

    private fun setClearDataButton(){
        clearDataButton = findViewById(R.id.clearDataButton)
        clearDataButton.setOnClickListener(){
            val gameDbHandler = GameDBHandler(this, null, null, 1)
            gameDbHandler.clearTable()

            val userDBHandler = UserDBHandler(this, null, null, 1)
            userDBHandler.clearTable()

            finishAffinity()
        }
    }

    private fun setListOfAdditionsButton(){
        listOfAdditionsButton = findViewById(R.id.listOfAdditionsButton)
        listOfAdditionsButton.setOnClickListener(){
            val intent = Intent(this, AdditionActivity::class.java)
            startActivity(intent)
        }
    }
}