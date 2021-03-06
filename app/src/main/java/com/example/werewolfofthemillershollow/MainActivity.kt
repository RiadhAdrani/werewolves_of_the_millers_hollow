package com.example.werewolfofthemillershollow

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.werewolfofthemillershollow.util.App

class MainActivity : App() {

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

    /**
     * Start a new game.
     * Opens up a new activity.
     * @see NewGameActivity
     */
    private fun startNewGame() {
        startActivity(Intent(applicationContext,NewGameActivity::class.java))
    }

    private fun loadSavedGame(){
        Toast.makeText(applicationContext,"Load Game",Toast.LENGTH_SHORT).show()
    }

    private fun startSettings(){
        Toast.makeText(applicationContext,"Settings",Toast.LENGTH_SHORT).show()
    }

    private fun startDocumentations(){
        startActivity(Intent(applicationContext, DocumentationActivity::class.java))
    }

    private fun displayMoreInfo(){
        Toast.makeText(applicationContext,"Display More Info",Toast.LENGTH_SHORT).show()
    }

}