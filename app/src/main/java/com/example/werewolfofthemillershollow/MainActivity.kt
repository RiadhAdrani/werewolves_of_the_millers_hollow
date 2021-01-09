package com.example.werewolfofthemillershollow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newGame : CardView = findViewById(R.id.new_game_card)

        val loadGame : CardView = findViewById(R.id.load_game_card)
        val settings : CardView = findViewById(R.id.settings_card)
        val documentation : CardView = findViewById(R.id.documentation_card)
        val moreInfo : ImageView = findViewById(R.id.info)

        newGame.setOnClickListener {
            startNewGame()
        }

        loadGame.setOnClickListener {
            loadSavedGame()
        }

        settings.setOnClickListener {
            startSettings()
        }

        documentation.setOnClickListener {
            startDocumentations()
        }

        moreInfo.setOnClickListener {
            displayMoreInfo()
        }

    }

    private fun startNewGame() {
        Toast.makeText(applicationContext,"New Game",Toast.LENGTH_SHORT).show()
    }

    private fun loadSavedGame(){
        Toast.makeText(applicationContext,"Load Game",Toast.LENGTH_SHORT).show()
    }

    private fun startSettings(){
        Toast.makeText(applicationContext,"Settings",Toast.LENGTH_SHORT).show()
    }

    private fun startDocumentations(){
        Toast.makeText(applicationContext,"Documentation",Toast.LENGTH_SHORT).show()
    }

    private fun displayMoreInfo(){
        Toast.makeText(applicationContext,"Display More Info",Toast.LENGTH_SHORT).show()
    }

}