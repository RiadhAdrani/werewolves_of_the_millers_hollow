package com.example.werewolfofthemillershollow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.werewolfofthemillershollow.roles.Role

/**
 * Activity allowing the user to customize his game,
 * in addition to distributing role to players in a random way.
 * @see Role
 */
class NewGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_game)
    }
}