package com.example.werewolfofthemillershollow

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

/**
 * Manage how game is played and progressed.
 * @see NewGameActivity
 */
class GameActivity : AppCompatActivity() {

    /**
     * Allow the game master to kick the current player.
     */
    private lateinit var kickButton : ImageView

    /**
     * Display the current player name.
     */
    private lateinit var playerName : TextView

    /**
     * Allow the access to more options.
     */
    private lateinit var moreOptions : ImageView

    /**
     * Display the icon of the currently playing role.
     */
    private lateinit var roleIcon : ImageView

    /**
     * Display the name of the currently playing role.
     */
    private lateinit var roleName : TextView

    /**
     * Display the different status effects applied to this player.
     */
    private lateinit var statusEffects : RecyclerView

    /**
     * Display needed info for the game master/narrator.
     */
    private lateinit var narratorText : TextView

    /**
     * Allow the current player to use his first ability.
     */
    private lateinit var abilityOne : ImageView

    /**
     * Allow the current player to use his secondary ability if he has one.
     */
    private lateinit var abilityTwo : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        kickButton = findViewById(R.id.kick_player)
        kickButton.setOnClickListener {
            //TODO : onClickListener
        }

        playerName = findViewById(R.id.player_name)

        moreOptions = findViewById(R.id.more_options)
        moreOptions.setOnClickListener {
            //TODO : onClickListener
        }

        roleIcon = findViewById(R.id.role_icon)

        roleName = findViewById(R.id.role_name)

        statusEffects = findViewById(R.id.status_effects)

        narratorText = findViewById(R.id.narrator_info)

        abilityOne = findViewById(R.id.ability_one)
        abilityOne.setOnClickListener {
            //TODO : onClickListener
        }

        abilityTwo = findViewById(R.id.ability_two)
        abilityTwo.setOnClickListener {
            //TODO : onClickListener
        }

    }
}