package com.example.werewolfofthemillershollow

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.roles.*
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.turn.*
import com.example.werewolfofthemillershollow.utility.*

import java.lang.Exception

/**
 * Manage how game is played and progressed.
 * @see NewGameActivity
 */
class GameActivity : AppCompatActivity() {

    /**
     * Current player turn index
     */
    private var index : Int = 0

    /**
     * Turn count
     */
    private var round : Int = 1

    /**
     * List of alive players
     */
    lateinit var playerList : ArrayList<Role>

    /**
     * List of dead players
     */
    lateinit var deadList : ArrayList<Role>

    /**
     * List of current roles turns
     */
    lateinit var turnList : ArrayList<Turn<*>>

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
     * Adapter for status effect recycler view.
     */
    private lateinit var statusEffectAdapter : StatusEffectAdapter

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

    /**
     * Allow the user to skip this player
     */
    private lateinit var skip : ImageView

    /**
     * Display the current turn count.
     */
    private lateinit var turn : TextView

    var servantRef : Servant? = null

    lateinit var captainRef: Role

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

        turn = findViewById(R.id.turn_count)
        setTurn()

        skip = findViewById(R.id.skip)
        skip.setOnClickListener {
            next()
        }

        roleIcon = findViewById(R.id.role_icon)

        roleName = findViewById(R.id.role_name)

        statusEffects = findViewById(R.id.status_effects)
        statusEffectAdapter = StatusEffectAdapter(list = ArrayList(),context = baseContext, listener = null)
        statusEffects.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        statusEffects.adapter = statusEffectAdapter

        narratorText = findViewById(R.id.narrator_info)

        abilityOne = findViewById(R.id.ability_one)

        abilityTwo = findViewById(R.id.ability_two)

        deadList = ArrayList()

        playerList = intent.getSerializableExtra("list") as ArrayList<Role>

        for (role : Role in playerList){
            role.debug(tag = "Role")
        }

        turnList = createTurns(playerList)

        displayNext()
    }

    /**
     * Update role icon drawable with a new one.
     * @param icon new icon id
     */
    private fun setIcon(icon : Int){
        if (icon == -1) return
        roleIcon.setImageResource(icon)
    }

    /**
     * Update the role textView.
     * @param name new role name
     */
    private fun setRole(name : String){
        roleName.text = name
    }

    /**
     * Update the player name TextView.
     * @param name new name
     */
    private fun setName(name : String){
        playerName.text = name
    }

    /**
     * Update narrator instructions TextView.
     * @param text new text
     */
    private fun setInstructions(text : String){
        narratorText.text = text
    }

    /**
     * Update the text of turn TextView with round.
     */
    private fun setTurn(){
        turn.text = round.toString()
    }

    /**
     * Update primary ability icon.
     * @param icon new icon
     */
    private fun setPrimaryIcon(icon : Int){
        abilityOne.setImageResource(icon)
    }

    /**
     * Update secondary ability icon.
     * @param icon new icon
     */
    private fun setSecondaryIcon(icon : Int){
        abilityTwo.setImageResource(icon)
    }

    /**
     * Create a list of ordered turns from the list of players.
     * @param list list of player
     * @return list of turns in order
     */
    private fun createTurns(list : ArrayList<Role>) : ArrayList<Turn<*>> {

        val output = ArrayList<Turn<*>>()

        val servant = ServantTurn(Servant(baseContext))
        if (servant.addTurn(output = output, list = list, baseContext)) {
            for (role : Role in playerList){
                if (role.getName() == getString(R.string.servant_name)){
                    servantRef = role as Servant
                    break
                }
            }
            Log.d("Role","servantRef = ${this.servantRef!!.getName()} : ${this.servantRef!!.getPlayer()}")
        }

        val guardian = GuardianTurn(Guardian(baseContext))
        guardian.addTurn(output = output, list = list, baseContext)

        val wolfpack = WolfpackTurn(Werewolf(baseContext))
        wolfpack.addTurn(output = output, list = list, baseContext)

        val infect = InfectTurn(FatherOfWolves(baseContext))
        infect.addTurn(output = output, list = list, baseContext)

        val sorcerer = SorcererTurn(Sorcerer(baseContext))
        sorcerer.addTurn(output = output, list = list, baseContext)

        val seer = SeerTurn(Seer(baseContext))
        seer.addTurn(output = output, list = list, baseContext)

        val knight = KnightTurn(Knight(baseContext))
        knight.addTurn(output = output, list = list, baseContext)

        val barber = BarberTurn(Barber(baseContext))
        barber.addTurn(output = output, list = list, baseContext)

        val captain = CaptainTurn(Captain(baseContext))
        if (captain.addTurn(output = output, list = list, baseContext)) {
            for (role : Role in playerList){
                if (role.getIsCaptain()!!){
                    captainRef = role
                    break
                }
            }
            Log.d("Role","captainRef = ${this.captainRef.getName()} : ${this.captainRef.getPlayer()}")
        }


        return output
    }

    /**
     * Update the information displayed in the activity.
     */
    fun displayNext(){

        val currentPlayer = turnList[index]

        try {
            setName(currentPlayer.getPlayer(list = playerList))
        }catch (e : Exception){
            Log.d("DEBUG_ROLE","$e")
        }finally {
            currentPlayer.getRole().debug()
        }

        setIcon(currentPlayer.getIcon())
        setPrimaryIcon(currentPlayer.getPrimaryIcon())
        setSecondaryIcon(currentPlayer.getSecondaryIcon())
        setRole(currentPlayer.getRoleToDisplay(context = baseContext, list = playerList))
        setInstructions(currentPlayer.getInstructions(context = baseContext, list = playerList))
        statusEffectAdapter.setList(currentPlayer.getRole().getStatusEffects())

        val onDismissed = object : UsePowerDialog.OnDismissed{

            override fun onDismissed() {
                next()
            }

        }

        if (currentPlayer.onStart(this)!!){

            val dialog = UsePowerDialog(
                currentPlayer,
                currentPlayer.getRole().getIcon()!!,
                playerList,
                deadList,
                currentPlayer.getOnStartTargets(playerList),
                currentPlayer.getOnStartOnClickHandler(),
                currentPlayer.getOnStartOnTargetHandler(),
                null,
                false,
                this
            )

            dialog.show(supportFragmentManager, App.TAG_ALERT)
            return
        }

        abilityOne.setOnClickListener {

            if (!currentPlayer.canPrimary()){
                Toast.makeText(baseContext,R.string.cant_use_power,Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (currentPlayer.getHasPrimary()){
                val dialog = UsePowerDialog(
                    currentPlayer,
                    currentPlayer.getPrimaryIcon(),
                    playerList,
                    deadList,
                    currentPlayer.getTargetsPrimary(playerList),
                    currentPlayer.getPrimaryOnClickHandler(),
                    currentPlayer.getPrimaryOnTargetHandler(),
                    onDismissed,
                    true,
                    this
                )

                dialog.show(supportFragmentManager, App.TAG_ALERT)
            }
            else {
                Toast.makeText(baseContext,R.string.no_power,Toast.LENGTH_LONG).show()
            }

        }

        abilityTwo.setOnClickListener {

            if (!currentPlayer.canSecondary()){
                Toast.makeText(baseContext,R.string.cant_use_power,Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (currentPlayer.getHasSecondary()){
                val dialog = UsePowerDialog(
                    currentPlayer,
                    currentPlayer.getSecondaryIcon(),
                    playerList,
                    deadList,
                    currentPlayer.getTargetsSecondary(playerList),
                    currentPlayer.getSecondaryOnClickHandler(),
                    currentPlayer.getSecondaryOnTargetHandler(),
                    onDismissed,
                    true,
                    this
                )

                dialog.show(supportFragmentManager, App.TAG_ALERT)
            }
            else {
                Toast.makeText(baseContext,R.string.no_power,Toast.LENGTH_LONG).show()
            }

        }

    }

    /**
     * Move to the next role.
     * Uses Recursive call.
     */
    private fun next(){

        if (index == turnList.size-1){
            index = 0
            round ++
            setTurn()
        }
        else
            index ++

        if (!turnList[index].canPlay(round = round, list = playerList))
            next()
        else
            displayNext()

    }

    fun findServant() : Servant?{

        for (role : Role in playerList){
            if (role.javaClass == Servant::javaClass){
                return role as Servant
            }
        }

        return null
    }

    fun replaceServant(role : Role) : Role?{

        for (player : Role in playerList){
            if (player.javaClass == Servant::javaClass){
                val i = playerList.indexOf(player)
                Log.d("Servant","Size = ${playerList.size}")
                Log.d("Servant","Player $i is ${playerList[i].getName()}")
                playerList[i] = role.new(baseContext, player.getPlayer()!!)!!
                servantRef = null
                Log.d("Servant","Player $i become ${playerList[i].getName()}")
                Log.d("Servant","Size ${playerList.size}")

                return playerList[i]
            }
        }

        return null
    }

}