package com.example.werewolfofthemillershollow

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.utility.RoleAdapter

/**
 * Activity allowing the user to customize his game,
 * in addition to distributing role to players in a random way.
 * @see Role
 */
class NewGameActivity : AppCompatActivity() {

    /**
     * Recycler view for currently selected players
     */
    private lateinit var currentPlayersRV : RecyclerView

    /**
     * Adapter for current player recycler view
     */
    private lateinit var currentPlayersAdapter : RoleAdapter

    /**
     * Recycler view for available roles
     */
    private lateinit var availableRolesRV : RecyclerView

    /**
     * Adapter for available roles recycler view
     */
    private lateinit var availableRolesAdapter : RoleAdapter

    private lateinit var numberOfPlayer : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_game)

        numberOfPlayer = findViewById(R.id.current_players_count)

        val generate : Button = findViewById(R.id.generate)
        generate.setOnClickListener {
            val number : Int = Integer.parseInt(numberOfPlayer.text.toString())
            currentPlayersAdapter.getList().clear()
            currentPlayersAdapter.setList(Role.getRoles(baseContext,number))
        }

        currentPlayersRV = findViewById(R.id.current_players_rv)
        availableRolesRV = findViewById(R.id.roles_rv)

        currentPlayersAdapter = RoleAdapter(context = applicationContext, list = ArrayList())
        currentPlayersRV.layoutManager = GridLayoutManager(baseContext,3)
        currentPlayersAdapter.setListener(listener = currentPlayersListener())
        currentPlayersRV.adapter = currentPlayersAdapter

        availableRolesAdapter = RoleAdapter(context = applicationContext, list = Role.getRoles(applicationContext))
        availableRolesRV.layoutManager = GridLayoutManager(baseContext, 3)
        availableRolesAdapter.setListener(listener = availableRoleListener())
        availableRolesRV.adapter = availableRolesAdapter

    }

    /**
     * Return an implementation of the interface needed for interaction and click even handling
     * in RoleAdapter for current players recycler view.
     * @see RoleAdapter
     * @see RoleAdapter.OnItemClick
     * @return Interface implementation
     */
    private fun currentPlayersListener() : RoleAdapter.OnItemClick{
        return object : RoleAdapter.OnItemClick{

            override fun onClick(position: Int) {
                val item = currentPlayersAdapter.getList()[position]
                currentPlayersAdapter.removeItem(position)
                availableRolesAdapter.addItem(item,0)
            }

            override fun onHold(position: Int): Boolean {
                return true
            }

        }
    }

    /**
     * Return an implementation of the interface needed for interaction and click even handling
     * in RoleAdapter for available role recycler view.
     * @see RoleAdapter
     * @see RoleAdapter.OnItemClick
     * * @return Interface implementation
     */
    private fun availableRoleListener() : RoleAdapter.OnItemClick{
        return object : RoleAdapter.OnItemClick{

            override fun onClick(position: Int) {
                val item = availableRolesAdapter.getList()[position]
                availableRolesAdapter.removeItem(position)
                currentPlayersAdapter.addItem(item,0)
            }

            override fun onHold(position: Int): Boolean {
                return true
            }

        }
    }
}